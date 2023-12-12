import {Component, OnInit} from '@angular/core';
import {FrontMenu} from "../../shared/interfaces/front-menu";
import {FrontMenuService} from "../../core/services/front-menu.service";


@Component({
  selector: 'app-navbar-menu',
  templateUrl: './navbar-menu.component.html',
  styleUrls: ['./navbar-menu.component.css']
})
export class NavbarMenuComponent implements OnInit{

  menus: FrontMenu[] = [];
  constructor(private frontMenuService: FrontMenuService) {
  }

  getMenu() {
    this.frontMenuService.getMenu().subscribe(menu => this.menus = menu.menus);
  }

  ngOnInit(): void {
    this.getMenu();
  }

}
