import { defineStore } from "pinia";
import { reactive, ref } from "vue";
import type { CardInGeneral } from "./modelInterfaces";

export const authStore = defineStore("login", ()=>{

    const isLoggedIn =  ref(false)
    const role = ref('')

    function setIsLoggedIn(bool:boolean) {
        isLoggedIn.value = bool
    }

    function setRole(personRole:string) {
        role.value = personRole
    }
    return {isLoggedIn,setIsLoggedIn, role, setRole}
})


export const ticketListStore = defineStore("tickets", () => {
    const tickets: CardInGeneral[] = reactive([])

    function setTickets(ticketList:Array<CardInGeneral>) {
        tickets.length = 0
        ticketList.forEach((ticket:CardInGeneral)=>tickets.push(ticket))
    }

    function deleteTicket(id:number) {
        setTickets(tickets.filter((item) => item.id !== id))
    }

    return {tickets, setTickets, deleteTicket}
})

