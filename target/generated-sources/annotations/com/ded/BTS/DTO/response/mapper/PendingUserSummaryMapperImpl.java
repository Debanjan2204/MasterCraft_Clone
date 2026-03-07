package com.ded.BTS.DTO.response.mapper;

import com.ded.BTS.enums.UserStatus;
import com.ded.BTS.model.PendingUserDetail;
import com.ded.BTS.model.RoleSummary;
import com.ded.BTS.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-07T20:30:02+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class PendingUserSummaryMapperImpl implements PendingUserSummaryMapper {

    @Override
    public PendingUserDetail toDto(User user) {
        if ( user == null ) {
            return null;
        }

        Long userId = null;
        String userName = null;
        List<RoleSummary> roleList = null;
        UserStatus status = null;

        userId = user.getId();
        userName = user.getUsername();
        roleList = map( user );
        status = user.getStatus();

        PendingUserDetail pendingUserDetail = new PendingUserDetail( userId, userName, status, roleList );

        return pendingUserDetail;
    }

    @Override
    public List<PendingUserDetail> toDtoList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<PendingUserDetail> list = new ArrayList<PendingUserDetail>( users.size() );
        for ( User user : users ) {
            list.add( toDto( user ) );
        }

        return list;
    }
}
