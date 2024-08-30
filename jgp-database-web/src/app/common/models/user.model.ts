export class User {
    id: number;
    profile: UserProfile;
    work: UserWork;
    contacts: UserContacts;
}

export class UserProfile {
    firstName: string;
    lastName: string;
    gender: string;
    image: string;
}

export class UserWork {
    partnerName: string;
    partnerId?: number;
    designation: string;
}

export class UserContacts {
    username: string;
    cellPhone: string;
    town: string;
}

