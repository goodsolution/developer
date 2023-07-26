import {Component} from "@angular/core";
import {Developer} from "../developer";
import {DeveloperService} from "../developer.service";

@Component({
  selector: 'app-navbar-developer-logo',
  templateUrl: './navbar-developer-logo.component.html',
  styleUrls: ['./navbar-developer-logo.component.css']
})
export class NavbarDeveloperLogoComponent {
  title = 'Developers';
  developers: Developer[] = [];

  constructor(private developerService: DeveloperService) {
  }

  ngOnInit(): void {
    this.getDevelopers();
  }

  getDevelopers(): void {
    this.developerService.getDevelopers()
      .subscribe(searchResultDeveloper=> this.developers = searchResultDeveloper.developers);
  }
}
