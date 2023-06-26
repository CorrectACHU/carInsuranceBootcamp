export interface TicketDetails {
    id:number,
    ticketOwner:Person
    dateOfIncident:string,
    ticketStatus: string,
}

export interface Person {
    id:number,
    email:string,
    role:string,
    contactInfo:ContactInfo
}

export interface ContactInfo {
    firstName:string,
    lastName:string,
    phones:Phone[],
    addresses:Address[]
}

interface Phone {
    id:number,
    phoneType:string,
    phoneNumber:string
}

interface Address {
    id:number,
    addressType:string,
    city:string,
    state:string,
    zip:string,
    addressInline:string
}