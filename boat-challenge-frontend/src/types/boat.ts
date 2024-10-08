export interface Boat extends BoatContent{
    id: number;
}

export interface BoatContent {
    name: string;
    description: string;
    date?: string;
    length?: number;
}