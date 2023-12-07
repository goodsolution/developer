import {Component, OnInit} from '@angular/core';
import {HeaderMenu} from "../../../models/header-menu.model";
import {HeaderMenuService} from "../../services/header-menu.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{
  menus!: HeaderMenu[];

  constructor(private headerMenuService: HeaderMenuService) {
  }

  getMenu() {
    this.headerMenuService.getMenu().subscribe(
      menu => this.menus = menu
    )

  }

  ngOnInit(): void {
    this.getMenu();
  }

}
