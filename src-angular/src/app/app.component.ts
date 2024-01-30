import {AfterViewInit, Component, OnInit, Type, ViewChild, ViewContainerRef} from '@angular/core';
import {CodeStatusService} from "./modules/core/services/code-status.service";
import {AntalHeaderComponent} from "./modules/core/components/header/antal-header/antal-header.component";
import {DodeHeaderComponent} from "./modules/core/components/header/dode-header/dode-header.component";
import {DefaultComponent} from "./modules/shared/default/default.component";
import {FooterAntalComponent} from "./modules/core/components/footer/footer-antal/footer-antal.component";
import {FooterDodeComponent} from "./modules/core/components/footer/footer-dode/footer-dode.component";
import {SearchResultCode} from "./modules/core/models/searchResultCode.model";
import {ContactAntalComponent} from "./modules/contact/contact-antal/contact-antal.component";
import {ContactDodeComponent} from "./modules/contact/contact-dode/contact-dode.component";
import {NavigationEnd, Router} from "@angular/router";

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
  @ViewChild('contactContainer', {read: ViewContainerRef, static: true})
  private contactContainer!: ViewContainerRef;
  title = 'app';
  statusCode!: SearchResultCode;

  constructor(private codeStatusService: CodeStatusService, private router: Router) {
  }

  ngAfterViewInit(): void {
    this.codeStatusService.fetchStatusCode().subscribe(headerType => {
      this.statusCode = headerType;
      console.log('code loadHeaderFooterDynamicComponents', this.statusCode.code);
      this.loadHeaderFooterDynamicComponents(this.statusCode.code);
    });
  }

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        if (event.url !== '/contact') {
          this.contactContainer.clear();
        }
      }
    });
    this.codeStatusService.getContactComponentTrigger().subscribe(() => {
      console.log('code loadDynamicComponents', this.statusCode.code);
      this.loadDynamicComponents(this.statusCode.code);
    });
  }

  private loadHeaderFooterDynamicComponents(codeStatus: string) {
    let headerComponent: Type<any>;
    let footerComponent: Type<any>;
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

  private loadDynamicComponents(codeStatus: string) {
    let contactComponent: Type<any>;
    switch (codeStatus) {
      case 'antal':
        contactComponent = ContactAntalComponent;
        break;
      case 'domdevelopment':
        contactComponent = ContactDodeComponent;
        break;
      default:
        contactComponent = DefaultComponent;
        break;
    }
    this.contactContainer.clear();
    this.contactContainer.createComponent(contactComponent);
  }

}

//TODO add contact page and home page to loader, no name in the contact link


