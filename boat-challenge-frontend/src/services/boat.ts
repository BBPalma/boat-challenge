import api from './api'; // Import the Axios instance
import { Boat, BoatContent } from '../types/boat';

// Fetch all boats (JWT token is automatically added to request headers)
export const fetchBoats = async (): Promise<Boat[]> => {
    const response = await api.get<Boat[]>('/boats');
    return response.data;
};

export const fetchBoatById = async (boatId: number): Promise<Boat> => {
    const response = await api.get<Boat>(`/boats/${boatId}`);
    return response.data;
};

// Add a new boat
export const addBoat = async (boat: BoatContent): Promise<Boat> => {
    const response = await api.post<Boat>('/boats', boat);
    return response.data;
};

// Update a boat
export const updateBoat = async (boatId: number, boat: BoatContent): Promise<Boat> => {
    const response = await api.put<Boat>(`/boats/${boatId}`, boat);
    return response.data;
};

// Delete a boat
export const deleteBoat = async (boatId: number): Promise<void> => {
    await api.delete(`/boats/${boatId}`);
};