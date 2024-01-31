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
import {HomeAntalComponent} from "./modules/home/home-antal/home-antal.component";
import {HomeDodeComponent} from "./modules/home/home-dode/home-dode.component";

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
  @ViewChild('homeContainer', {read: ViewContainerRef, static: true})
  private homeContainer!: ViewContainerRef;

  statusCode!: SearchResultCode;

  constructor(private codeStatusService: CodeStatusService, private router: Router) {
  }

  ngAfterViewInit(): void {
    this.codeStatusService.fetchStatusCode().subscribe(headerType => {
      this.statusCode = headerType;
      console.log('code loadHeaderFooterDynamicComponents', this.statusCode.code);
      this.loadHeaderFooterDynamicComponents(this.statusCode.code);
      console.log('code loadHomeDynamicComponent', this.statusCode.code);
      this.loadHomeDynamicComponent(this.statusCode.code);
    });
  }

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        // Clear the contact container if not on the contact page
        if (event.url !== '/contact') {
          this.contactContainer.clear();
        }
        // Check if the current URL is the root
        if (event.url === '/') {
          // Clear and reload the home component
          this.homeContainer.clear();
          if (this.statusCode) { // Ensure statusCode is available
            this.loadHomeDynamicComponent(this.statusCode.code);
          } else {
            // Fetch statusCode if not available
            this.codeStatusService.fetchStatusCode().subscribe(statusCode => {
              this.statusCode = statusCode;
              this.loadHomeDynamicComponent(this.statusCode.code);
            });
          }
        } else {
          // Clear the home container if not on the home page
          this.homeContainer.clear();
        }
      }
    });
    this.codeStatusService.getContactComponentTrigger().subscribe(() => {
      console.log('code loadContactDynamicComponents', this.statusCode.code);
      this.loadContactDynamicComponents(this.statusCode.code);
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

  private loadContactDynamicComponents(codeStatus: string) {
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

  private loadHomeDynamicComponent(codeStatus: string) {
    let homeComponent: Type<any>;
    switch (codeStatus) {
      case 'antal':
        homeComponent = HomeAntalComponent;
        break;
      case 'domdevelopment':
        homeComponent = HomeDodeComponent;
        break;
      default:
        homeComponent = DefaultComponent; // Your default home component
        break;
    }
    this.homeContainer.clear();
    this.homeContainer.createComponent(homeComponent);
  }

}
