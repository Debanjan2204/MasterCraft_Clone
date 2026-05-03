package com.ded.BTS.beans;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;

import com.ded.BTS.DTO.response.TicketCommentResponse;
import com.ded.BTS.DTO.response.TicketResponse;
import com.ded.BTS.model.Ticket;

public class TicketHashUtil {

    public static String generateHash(TicketResponse ticket, List<TicketCommentResponse> comments) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            StringBuilder sb = new StringBuilder();

            // Normalize description
            sb.append(normalize(ticket.description()));

            // Sort comments deterministically
            comments.stream()
            .sorted((c1, c2) -> c1.time().compareTo(c2.time()))
                    .forEach(c -> sb.append(normalize(c.content())));

            byte[] hash = digest.digest(sb.toString().getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String normalize(String input) {
        return input == null ? "" : input.trim().replaceAll("\\s+", " ");
    }
}