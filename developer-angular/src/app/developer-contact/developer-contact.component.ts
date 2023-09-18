import {Component, Input, OnInit} from '@angular/core';
import {Developer} from "../developer";
import {ActivatedRoute} from "@angular/router";
import {DeveloperService} from "../developer.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-developer-contact',
  templateUrl: './developer-contact.component.html',
  styleUrls: ['./developer-contact.component.css']
})
export class DeveloperContactComponent implements OnInit {
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
