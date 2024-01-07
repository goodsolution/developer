import {AfterViewInit, Component, OnInit, Type, ViewChild, ViewContainerRef} from '@angular/core';
import {CodeStatusService} from "./modules/core/services/code-status.service";
import {AntalHeaderComponent} from "./modules/core/components/header/antal-header/antal-header.component";
import {DodeHeaderComponent} from "./modules/core/components/header/dode-header/dode-header.component";
import {DefaultComponent} from "./modules/shared/default/default.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewInit {
  @ViewChild('headerContainer', {read: ViewContainerRef, static: true})
  private headerContainer!: ViewContainerRef
  title = 'app';
  code!: string;

  constructor(private codeStatusService: CodeStatusService) {
  }

  ngAfterViewInit(): void {
    this.codeStatusService.fetchHeaderType().subscribe(headerType => {
      this.code = headerType;
      console.log('code', this.code);
      this.loadHeaderComponent(this.code);
    });
  }

  ngOnInit() {
  }

  private loadHeaderComponent(headerTypeObject: any) {
    // Extract the 'code' property from the object
    const headerType = headerTypeObject.code;
    console.log('Extracted headerType:', headerType);
    let component: Type<any>;
    switch (headerType) {
      case 'antal':
        console.log('Loading AntalHeaderComponent');
        component = AntalHeaderComponent;
        break;
      case 'domdevelopment':
        console.log('Loading DodeHeaderComponent');
        component = DodeHeaderComponent;
        break;
      default:
        console.log('Loading DefaultComponent');
        component = DefaultComponent;
        break;
    }
    this.headerContainer.clear();
    this.headerContainer.createComponent(component);
  }

}
