import {AfterViewInit, Component, OnDestroy, OnInit, Type, ViewChild, ViewContainerRef} from '@angular/core';
import {CodeStatusService} from "./modules/core/services/code-status.service";
import {NavigationEnd, Router} from "@angular/router";
import {filter, Subscription} from 'rxjs';
import {SearchResultCode} from "./modules/core/models/searchResultCode.model";
import {AntalHeaderComponent} from "./modules/core/components/header/antal-header/antal-header.component";
import {DodeHeaderComponent} from "./modules/core/components/header/dode-header/dode-header.component";
import {DefaultComponent} from "./modules/shared/default/default.component";
import {FooterAntalComponent} from "./modules/core/components/footer/footer-antal/footer-antal.component";
import {FooterDodeComponent} from "./modules/core/components/footer/footer-dode/footer-dode.component";
import {ContactAntalComponent} from "./modules/contact/contact-antal/contact-antal.component";
import {ContactDodeComponent} from "./modules/contact/contact-dode/contact-dode.component";
import {HomeAntalComponent} from "./modules/home/home-antal/home-antal.component";
import {HomeDodeComponent} from "./modules/home/home-dode/home-dode.component";

enum ComponentLocation {
  Header, Footer, Contact, Home
}

interface ComponentConfig {
  [key: string]: {
    [location in ComponentLocation]: Type<any>;
  };
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('headerContainer', {read: ViewContainerRef, static: true}) private headerContainer!: ViewContainerRef;
  @ViewChild('footerContainer', {read: ViewContainerRef, static: true}) private footerContainer!: ViewContainerRef;
  @ViewChild('contactContainer', {read: ViewContainerRef, static: true}) private contactContainer!: ViewContainerRef;
  @ViewChild('homeContainer', {read: ViewContainerRef, static: true}) private homeContainer!: ViewContainerRef;

  private subscriptions: Subscription = new Subscription();
  private statusCode!: SearchResultCode;

  private componentConfig: ComponentConfig = {
    antal: {
      [ComponentLocation.Header]: AntalHeaderComponent,
      [ComponentLocation.Footer]: FooterAntalComponent,
      [ComponentLocation.Contact]: ContactAntalComponent,
      [ComponentLocation.Home]: HomeAntalComponent
    },
    domdevelopment: {
      [ComponentLocation.Header]: DodeHeaderComponent,
      [ComponentLocation.Footer]: FooterDodeComponent,
      [ComponentLocation.Contact]: ContactDodeComponent,
      [ComponentLocation.Home]: HomeDodeComponent
    },
    default: {
      [ComponentLocation.Header]: DefaultComponent,
      [ComponentLocation.Footer]: DefaultComponent,
      [ComponentLocation.Contact]: DefaultComponent,
      [ComponentLocation.Home]: DefaultComponent
    }
  };

  constructor(private codeStatusService: CodeStatusService, private router: Router) {
  }

  ngOnInit() {
    this.observeRouterEvents();
  }

  ngAfterViewInit() {
    this.loadDynamicComponents();
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  private loadDynamicComponents() {
    this.subscriptions.add(this.codeStatusService.fetchStatusCode().subscribe(statusCode => {
      this.statusCode = statusCode;
      this.createComponent(this.headerContainer, ComponentLocation.Header);
      this.createComponent(this.footerContainer, ComponentLocation.Footer);
      this.createComponent(this.homeContainer, ComponentLocation.Home);
    }));
  }

  private createComponent(container: ViewContainerRef, location: ComponentLocation) {
    if(!this.statusCode) return;
    const statusKey = this.statusCode.code || 'default';
    const component = this.componentConfig[statusKey][location] || DefaultComponent;
    container.clear();
    container.createComponent(component);
  }

  private observeRouterEvents() {
    this.subscriptions.add(this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)
    ).subscribe(event => {
      this.handleNavigationChange(event);
    }));
  }

  private handleNavigationChange(event: NavigationEnd) {
    const routesClearingMap = new Map<string, () => void>([
      ['/contact', () => this.createComponent(this.contactContainer, ComponentLocation.Contact)],
      ['/', () => this.createComponent(this.homeContainer, ComponentLocation.Home)],
      // ... more routes if necessary
    ]);

    // Clear all containers by default
    this.contactContainer.clear();
    this.homeContainer.clear();

    // Invoke the function corresponding to the current route, if it exists
    const routeAction = routesClearingMap.get(event.url);
    if (routeAction) routeAction();
  }

}
