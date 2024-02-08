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
import {
  InvestmentListAntalComponent
} from "./modules/investment-list/investment-list-antal/investment-list-antal.component";
import {
  InvestmentListDodeComponent
} from "./modules/investment-list/investment-list-dode/investment-list-dode.component";

enum ComponentLocation {
  Header, Footer, Contact, Home, InvestmentList
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
  @ViewChild('investmentListContainer', {read: ViewContainerRef}) private investmentListContainer!: ViewContainerRef;

  private subscriptions: Subscription = new Subscription();
  private statusCode!: SearchResultCode;

  private componentConfig: ComponentConfig = {
    antal: {
      [ComponentLocation.Header]: AntalHeaderComponent,
      [ComponentLocation.Footer]: FooterAntalComponent,
      [ComponentLocation.Contact]: ContactAntalComponent,
      [ComponentLocation.Home]: HomeAntalComponent,
      [ComponentLocation.InvestmentList]: InvestmentListAntalComponent,
    },
    domdevelopment: {
      [ComponentLocation.Header]: DodeHeaderComponent,
      [ComponentLocation.Footer]: FooterDodeComponent,
      [ComponentLocation.Contact]: ContactDodeComponent,
      [ComponentLocation.Home]: HomeDodeComponent,
      [ComponentLocation.InvestmentList]: InvestmentListDodeComponent,
    },
    default: {
      [ComponentLocation.Header]: DefaultComponent,
      [ComponentLocation.Footer]: DefaultComponent,
      [ComponentLocation.Contact]: DefaultComponent,
      [ComponentLocation.Home]: DefaultComponent,
      [ComponentLocation.InvestmentList]: DefaultComponent,
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

      // Add a condition to prevent loading investment components instantly
      // For example, only load if not on the root route (or based on some other condition)
      if (this.router.url !== '/') {
        this.createComponent(this.investmentListContainer, ComponentLocation.InvestmentList);
      }
    }));
  }

  private createComponent(container: ViewContainerRef, location: ComponentLocation) {
    if (!this.statusCode) return;
    const statusKey = this.statusCode.code || 'default';
    const component = this.componentConfig[statusKey][location] || DefaultComponent;
    container.clear();
    container.createComponent(component);
  }

  private observeRouterEvents() {
    this.subscriptions.add(this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)
    ).subscribe(event => {
      console.log('Navigation Event:', event.urlAfterRedirects);
      this.handleNavigationChange(event);
    }));
  }

  private handleNavigationChange(event: NavigationEnd) {
    console.log('Navigation Event:', event.urlAfterRedirects); // Log the navigation event URL
    const url = event.urlAfterRedirects.split('?')[0];
    const directMatchAction = this.routesClearingMap.get(url);
    if (directMatchAction) {
      console.log('Direct Match Action found for url:', url); // Log when a direct match is found
      directMatchAction();
    } else if (/^\/city\/.+/.test(url)) {
      console.log('City route matched:', url); // Log when a city route is matched
      this.loadDynamicInvestmentComponent(url);
    } else {
      console.log('No specific action found for url:', url); // Log when no specific action is matched
    }
  }

  private routesClearingMap = new Map<string, () => void>([
    ['/contact', () => {
      this.homeContainer.clear();
      this.investmentListContainer.clear();
      this.contactContainer.clear();
      this.createComponent(this.contactContainer, ComponentLocation.Contact);
    }],
    ['/', () => {
      this.homeContainer.clear();
      this.createComponent(this.homeContainer, ComponentLocation.Home);
    }],
    ['/login', () => {
      this.homeContainer.clear();
      this.investmentListContainer.clear();
      this.contactContainer.clear();
    }],
    // Add more route actions as needed
  ]);

  private loadDynamicInvestmentComponent(url: string) {
    // Extract the city name from the URL if needed, or directly load the component
    // Example extraction (assuming URL is something like '/city/new-york'):
    const cityName = url.split('/')[2]; // This will give 'new-york' for '/city/new-york'

    // Use cityName or other logic to decide which component to load
    // Assuming you've a method to dynamically load components like:
    this.createComponent(this.investmentListContainer, ComponentLocation.InvestmentList);
  }

}
