import axios from 'axios'
import { Credentials } from '../types/credentials'

const baseUrl = import.meta.env.VITE_SERVER_URL + '/auth/'

const login = async (credentials: Credentials) => {
    const response = await axios.post(baseUrl + 'login', credentials)
    return response.data
}

const signup = async (userInfo: Credentials) => {
    const response = await axios.post(baseUrl + 'signup', userInfo)
    return response.data
}

export default {
    login,
    signup
}