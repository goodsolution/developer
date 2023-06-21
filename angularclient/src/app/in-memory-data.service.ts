import {Injectable} from '@angular/core';
import {InMemoryDbService} from "angular-in-memory-web-api";
import {Premise} from "./premise";

@Injectable({
  providedIn: 'root'
})
export class InMemoryDataService implements InMemoryDbService {

  constructor() {
  }

  // @ts-ignore
  createDb() {
    const premises = [
      {id: 1, name: 'Premise 1', address: 'Address 1', city: 'City 1', country: 'Country 1', owner: 'Owner 1'},
      {id: 2, name: 'Premise 2', address: 'Address 2', city: 'City 2', country: 'Country 2', owner: 'Owner 2'},
      {id: 3, name: 'Premise 3', address: 'Address 3', city: 'City 3', country: 'Country 3', owner: 'Owner 3'},
      {id: 4, name: 'Premise 4', address: 'Address 4', city: 'City 4', country: 'Country 4', owner: 'Owner 4'},
      {id: 5, name: 'Premise 5', address: 'Address 5', city: 'City 5', country: 'Country 5', owner: 'Owner 5'},
      {id: 6, name: 'Premise 6', address: 'Address 6', city: 'City 6', country: 'Country 6', owner: 'Owner 6'},
    ];
    return {premises};
  }

  // Overrides the genId method to ensure that a hero always has an id.
  // If the heroes array is empty,
  // the method below returns the initial number (11).
  // if the heroes array is not empty, the method below returns the highest
  // hero id + 1.
  genId(premises: Premise[]): number {
    return premises.length > 0 ? Math.max(...premises.map(hero => hero.id)) + 1 : 11;
  }

}
