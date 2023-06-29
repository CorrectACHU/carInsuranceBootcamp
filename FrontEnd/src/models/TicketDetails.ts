export interface TicketDetailsInterface {
    id:number,
    dateOfIncident:string,
    insuranceCompany:Company
    ticketOwner:Person,
    currentManager:Person,
    currentEstimator:Person,
    ticketStatus: string,
    vehicleInfo: VehicleInformation,
    vehicleCondition: VehicleCondition,
    estimatedParts: EstimatedPart[],
    otherCharge: OtherCharge,
    otherContacts: ContactInfo[],
    supplement: Supplement,
    comments: Comment[],
}

export interface Person {
    id:number,
    email:string,
    role:string,
    contactInfo:ContactInfo
}

export interface ContactInfo {
    id:number,
    email:string,
    contactType:string,
    firstName:string,
    lastName:string,
    notes:string,
    phones:Phone[],
    addresses:Address[]
}

interface Phone {
    id:number,
    phoneType:string,
    phoneNumber:string
}

export interface Address {
    id:number,
    addressType:string,
    city:string,
    state:string,
    zip:string,
    addressInline:string
}

interface Company {
    id:number,
    companyName: string,
    companyType: number
}

interface VehicleInformation {
    id:number,
    year: string
    make:string,
    model:string,
    licensePlate: string,
    licenseState:string,
    licenseExpiration:string,
    odometerValue:string
}

interface VehicleCondition {
    id:number,
    impactDirection: string
    photoFileNames: string
}

interface EstimatedPart {
    id:number,
    estimateType: string,
    description: string,
    laborHours: string,
    price: number, 
    laborRate: string
}

interface OtherCharge {
    id:number,
    towing: string,
    storage: string
}


interface Supplement {
    id:number,
    estimatedParts: EstimatedPart[]
    otherCharge: OtherCharge
}

interface Comment {
    id:number,
    commentOwner: Person,
    ticketId: TicketDetailsInterface,
    body: string,
    createdDate: string
}

