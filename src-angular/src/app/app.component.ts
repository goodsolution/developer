import {AfterViewInit, Component, OnInit, Type, ViewChild, ViewContainerRef} from '@angular/core';
import {CodeStatusService} from "./modules/core/services/code-status.service";
import {AntalHeaderComponent} from "./modules/core/components/header/antal-header/antal-header.component";
import {DodeHeaderComponent} from "./modules/core/components/header/dode-header/dode-header.component";
import {DefaultComponent} from "./modules/shared/default/default.component";
import {FooterAntalComponent} from "./modules/core/components/footer/footer-antal/footer-antal.component";
import {FooterDodeComponent} from "./modules/core/components/footer/footer-dode/footer-dode.component";
import {SearchResultCode} from "./modules/core/models/searchResultCode.model";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewInit {
  @ViewChild('headerContainer', {read: ViewContainerRef, static: true})
  private headerContainer!: ViewContainerRef;
  @ViewChild('footerContainer', {read: ViewContainerRef, static: true})
  private footerContainer!: ViewContainerRef;
  title = 'app';
  statusCode!: SearchResultCode;

  constructor(private codeStatusService: CodeStatusService) {
  }

  ngAfterViewInit(): void {
    this.codeStatusService.fetchStatusCode().subscribe(headerType => {
      this.statusCode = headerType;
      console.log('code', this.statusCode.code);
      this.loadDynamicComponents(this.statusCode.code);
    });
  }

  ngOnInit() {
  }

  private loadDynamicComponents(codeStatus: string) {
    let headerComponent: Type<any>;
    let footerComponent: Type<any>
    switch (codeStatus) {
      case 'antal':
        headerComponent = AntalHeaderComponent;
        footerComponent = FooterAntalComponent;
        break;
      case 'domdevelopment':
        headerComponent = DodeHeaderComponent;
        footerComponent = FooterDodeComponent;
        break;
      default:
        headerComponent = DefaultComponent;
        footerComponent = DefaultComponent;
        break;
    }
    this.headerContainer.clear();
    this.footerContainer.clear();
    this.headerContainer.createComponent(headerComponent);
    this.footerContainer.createComponent(footerComponent);
  }
}


