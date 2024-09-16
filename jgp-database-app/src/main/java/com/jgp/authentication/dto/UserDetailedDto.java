package com.jgp.authentication.dto;

import java.util.Set;

public record UserDetailedDto(
        Long id,
        UserProfileDto profile,
        UserWorkDto work,
        UserContactsDto contacts

) {
    public record UserProfileDto(
            String firstName,
            String lastName,
            String gender,
            String image,
            Set<String> userRoles
    ){}

    public record UserWorkDto(
            String partnerName,
            Long partnerId,
            String designation
    ){}

    public record UserContactsDto(
            String username,
            String cellPhone,
            String town
    ){}

}
