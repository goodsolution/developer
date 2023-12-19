import {Component, OnInit} from '@angular/core';
import {HomePagePhotoService} from "../core/services/home-page-photo.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  photoUrl!: string;

  constructor(private photoService: HomePagePhotoService) {
  }

  getUrl(){
    this.photoService.getPhoto().subscribe(
      photoUrl => this.photoUrl = photoUrl.url
    )
  }

  ngOnInit() {
    this.getUrl();
  }


}
