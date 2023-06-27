import router from "@/router"

export const redirectByPersonRole = (role:string) => {
    switch (role) {
    case 'USER':
      router.push({name:'userTickets'})
      break
    case 'MANAGER':
      router.push('/manager')
      break
    case 'ESTIMATOR':
      router.push('/estimator')
      break
    default:
      return
  }}

export const getTokenFromCookie = () => {
    const cookies = document.cookie
    const cookiesArr = cookies.split(';')
    const token = cookiesArr.find((cookie) => cookie.includes('token'))
    if (token) {
      return token.replace('token=', '')
    }
    return ''
  }

export const getRoleFromCookie = () => {
    const cookies = document.cookie
    const cookiesArr = cookies.split(';')
    const role = cookiesArr.find((cookie) => cookie.includes('role'))
    if (role) {
      return role.replace('role=', '').trim()
    }
    return ''
  }


export const cleanCookie = () => {
    document.cookie = 'token' + '=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;'
}