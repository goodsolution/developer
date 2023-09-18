import {Component, OnInit} from "@angular/core";
import {FrontMenuService} from "../front-menu.service";
import {FrontMenu} from "../front-menu";

@Component({
  selector: 'app-navbar-developer',
  templateUrl: './navbar-developer.component.html',
  styleUrls: ['./navbar-developer.component.css']
})
export class NavbarDeveloper implements OnInit {

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
