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
  isBalcony: number;
  isGarden: number;
  isTerrace: number;
  isLoggia: number;
  buildingId: number;

}
