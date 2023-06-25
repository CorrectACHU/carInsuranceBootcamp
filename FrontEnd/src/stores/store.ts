import { defineStore } from "pinia";
import { ref } from "vue";

export const authStore = defineStore("login", ()=>{

    const isLoggedIn =  ref(false)
    const role = ref('')

    function changeIsLoggedInToTrue() {
        isLoggedIn.value = true
    }

    function changeIsLoggedInToFalse() {
        isLoggedIn.value = false
    }

    function setRole(personRole:string) {
        role.value = personRole
    }
    return {isLoggedIn, changeIsLoggedInToTrue,changeIsLoggedInToFalse, role, setRole}
})


