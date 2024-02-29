export interface PremiseResponse {
  id: number;
  type: string;
  number: number;
  floor: number;
  surfacePerSqMeter: number;
  pricePerSqMeter: number;
  totalPrice: number;
  numberOfRooms: number;
  technicalStatus: string;
  salesStatus: string;
  exposure: string;
  isBalcony: boolean;
  isGarden: boolean;
  isTerrace: boolean;
  isLoggia: boolean;
  buildingId: number;

}
