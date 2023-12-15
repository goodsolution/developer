import {Component, Input} from '@angular/core';
import {Developer} from "../../shared/interfaces/developer";
import {ActivatedRoute} from "@angular/router";
import {DeveloperService} from "../../core/services/developer.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-navbar-contact',
  templateUrl: './navbar-contact.component.html',
  styleUrls: ['./navbar-contact.component.css']
})
export class NavbarContactComponent {
  @Input() developers?: Developer[];

  constructor(
    private route: ActivatedRoute,
    private developerService: DeveloperService,
    private location: Location
  ) {
  }

  ngOnInit(): void {
    this.getDeveloper();
  }

  getDeveloper(): void{
    this.developerService.getDevelopers().subscribe(developer => this.developers = developer.developers)
  }
}
