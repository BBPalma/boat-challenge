export interface Boat extends BoatContent{
    id: number;
}

export interface BoatContent {
    name: string;
    description: string;
    date?: Date;
    length?: number;
}