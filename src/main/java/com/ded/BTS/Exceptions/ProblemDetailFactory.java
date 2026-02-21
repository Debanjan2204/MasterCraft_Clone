package com.ded.BTS.Exceptions;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

import jakarta.annotation.Nullable;


@Component
public class ProblemDetailFactory {

	public ProblemDetail build(HttpStatus status, String title, String detail, String errorCode, String path, @Nullable Object errors) {
		
		ProblemDetail problemDetail=ProblemDetail.forStatus(status);
		problemDetail.setTitle(title);
		problemDetail.setDetail(detail);
		problemDetail.setType(URI.create("https://api.bts/errors/" + errorCode));
		problemDetail.setInstance(URI.create(path));
		if (errors != null) {
			problemDetail.setProperty("errors", errors);
        }		problemDetail.setProperty("timestamp", Instant.now());
		
		return problemDetail;
	}

}
