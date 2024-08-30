package com.jgp.authentication.dto;

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
            String image
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
