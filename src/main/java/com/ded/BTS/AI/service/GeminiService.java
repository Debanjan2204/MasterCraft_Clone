package com.ded.BTS.AI.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ded.BTS.AI.AiService;
import com.ded.BTS.DTO.response.TicketCommentResponse;
import com.ded.BTS.DTO.response.TicketResponse;
import com.ded.BTS.beans.TicketHashUtil;
import com.ded.BTS.model.Ticket;
import com.ded.BTS.model.TicketAiSummary;
import com.ded.BTS.model.TicketSummaryId;
import com.ded.BTS.repository.TicketAiSummaryRepo;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class GeminiService implements AiService {

    private final Jackson2ObjectMapperBuilderCustomizer jsonCustomizer;

    @Value("${spring.ai.openai.api-key}")
	private   String API_KEY ;
    @Value("${spring.ai.openai.base-url}")
	private   String baseUrl ;
	
	
	private final WebClient webClient;
	private final TicketAiSummaryRepo ticketAiSummaryRepo;
	 public GeminiService(WebClient webClient,TicketAiSummaryRepo ticketAiSummaryRepo, Jackson2ObjectMapperBuilderCustomizer jsonCustomizer) {
	        this.webClient = webClient;
	        this.ticketAiSummaryRepo=ticketAiSummaryRepo;
	        this.jsonCustomizer = jsonCustomizer;
	    }

	public String summarize(String prompt) {

        Map<String, Object> requestBody = Map.of(
            "contents", List.of(
                Map.of(
                    "parts", List.of(
                        Map.of("text", prompt)
                    )
                )
            )
        );

        return webClient.post()
                .uri(baseUrl + "/models/gemini-flash-latest:generateContent")
                .header("Content-Type", "application/json")
                .header("X-goog-api-key", API_KEY)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(response -> response
                        .path("candidates")
                        .get(0)
                        .path("content")
                        .path("parts")
                        .get(0)
                        .path("text")
                        .asText()
                )
                .block(); // fine for now, optimize later
    }

	@Override
	public String summarizeComments(List<TicketCommentResponse> comments, TicketResponse ticket, String userName) {

	    String hash = TicketHashUtil.generateHash(ticket, comments);

	    return ticketAiSummaryRepo.findByTicketIdAndContentHash(ticket.id(), hash)
	        .map(TicketAiSummary::getSummaryJson)
	        .orElseGet(() -> generateAndStore(ticket, comments, hash,userName));

	}
	public String TEST(String prompt) {


		return summarize(prompt);
	}
	@Override
	public String chat(String prompt) {
		return null;
	}

	private String buildSummaryPrompt(List<TicketCommentResponse> comments, String ticketDetail, String userName) {
		return """
				YOU ARE A PROFFESIONAL DEVELOPER WORKING AMONG A PRODUCTION SUPPORT TEAM ,HAVING WHOLE DETAILED KNOWLEDGE OF OUR SYSTEM.

				YOUR TASK IS TO SUMMARIZE THE FOLLOWING COMMENTS OF A TICKET AND GENERATE THE BELOW DETAILS -
				   1. KEY DECISIONS NEEDED
				   2. ISSUES RAISED UNDER THE COMMENTS
				   3. ACTION ITEMS TO BE PERFORMED TO CLOSE THE TICKET
				   
				OUTPUT STRUCTURE- JSON - 
				{
				  "KEY DECISIONS": "",
				  "ISSUES RAISED": "",
				  "ACTION ITEMS":""
				}
					
				CONSTRAINTS-
				1. COMMENTS WILL BE IN "AUTHOR_NAME~|~COMMET_CONTENT" FORMAT.
				2. CONTEXT WILL CONTAIN TICKET DETAILS IN "TICKET_TITLE~|~TICKET_DESCRIPTION" FORMAT.
				3. REFRAIN FROM USING PERSON BASED ACTION ITEMS AND SHARE GENERALIZED ACTION ITEMS. DO NOT HALLUCINATE OR ASSUME DETAILS.
				4. RESPONSE SHOULD BE AS SUGGESTED. ANY OTHER TYPE OF EXTRA RESPONSE WILL MAKE THE WHOLE RESPONSE INVALID.


				CONTEXT- %s
				COMMENTS- %s
				   """
				.formatted( ticketDetail,
						comments.stream().sorted((c1, c2) -> c1.time().compareTo(c2.time())).map(comment -> comment.author().userName() + ":" + comment.content())
								.collect(Collectors.joining(","))); 

	}

	
	private String generateAndStore(TicketResponse ticket, List<TicketCommentResponse> comments, String hash,String userName) {

		System.out.println(">>> Summary not ready, Hitting Gemini for creating summary");
		String prompt = buildSummaryPrompt(comments, ticket.title()+":"+ticket.description(), userName);
	    String summary = summarize(prompt);

	    try {
	        ticketAiSummaryRepo.save( new TicketAiSummary(ticket.id(), hash, summary));
	        return summary;

	    } catch (Exception e) {
	        // Another thread already inserted
	        return ticketAiSummaryRepo.findByTicketIdAndContentHash(ticket.id(), hash)
	                .orElseThrow(()-> new RuntimeException("Couldnot find response"))
	                .getSummaryJson();
	    }
	}
}
