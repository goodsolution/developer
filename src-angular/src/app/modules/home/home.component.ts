import {Component} from '@angular/core';
import {CodeStatusService} from "../core/services/code-status.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  code!: string;

  constructor(
    private codeService: CodeStatusService) {
  }

  getCode() {
    this.codeService.getDeveloperStatus().subscribe(data => {
      this.code = data.code;
    });
  }

  ngOnInit(): void {
    this.getCode();
  }

}
