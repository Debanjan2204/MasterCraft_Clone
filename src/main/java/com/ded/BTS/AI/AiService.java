package com.ded.BTS.AI;

import java.util.List;

import com.ded.BTS.DTO.response.TicketCommentResponse;
import com.ded.BTS.DTO.response.TicketResponse;
import com.ded.BTS.model.Ticket;

public interface AiService {

    String summarizeComments(List<TicketCommentResponse> comments,TicketResponse ticket ,String userName);

    String chat(String prompt); // keep generic for future use
    
	public String TEST(String prompt);
}