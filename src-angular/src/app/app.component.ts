import {Component, OnDestroy, OnInit, Type, ViewChild, ViewContainerRef} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {filter, Subscription, take} from 'rxjs';
import {SearchResultCode} from "./modules/core/models/searchResultCode.model";
import {DynamicComponentLoadingService} from "./modules/core/services/dynamic-component-loading.service";
import {
  InvestmentListAntalComponent
} from "./modules/investment-list/investment-list-antal/investment-list-antal.component";
import {
  InvestmentListDodeComponent
} from "./modules/investment-list/investment-list-dode/investment-list-dode.component";
import {AntalHeaderComponent} from "./modules/core/components/header/antal-header/antal-header.component";
import {FooterAntalComponent} from "./modules/core/components/footer/footer-antal/footer-antal.component";
import {ContactAntalComponent} from "./modules/contact/contact-antal/contact-antal.component";
import {HomeAntalComponent} from "./modules/home/home-antal/home-antal.component";
import {DodeHeaderComponent} from "./modules/core/components/header/dode-header/dode-header.component";
import {FooterDodeComponent} from "./modules/core/components/footer/footer-dode/footer-dode.component";
import {ContactDodeComponent} from "./modules/contact/contact-dode/contact-dode.component";
import {HomeDodeComponent} from "./modules/home/home-dode/home-dode.component";
import {DefaultComponent} from "./modules/shared/default/default.component";
import {CodeStatusService} from "./modules/core/services/code-status.service";


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
export class AppComponent implements OnInit, OnDestroy {

  @ViewChild('headerContainer', {read: ViewContainerRef, static: true}) private headerContainer!: ViewContainerRef;
  @ViewChild('footerContainer', {read: ViewContainerRef, static: true}) private footerContainer!: ViewContainerRef;
  @ViewChild('contactContainer', {read: ViewContainerRef, static: true}) private contactContainer!: ViewContainerRef;
  @ViewChild('homeContainer', {read: ViewContainerRef, static: true}) private homeContainer!: ViewContainerRef;
  @ViewChild('investmentListContainer', {read: ViewContainerRef}) private investmentListContainer!: ViewContainerRef;

  private subscriptions: Subscription = new Subscription();
  private statusCode: SearchResultCode | null = null;

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

  constructor(
    private codeStatusService: CodeStatusService,
    private router: Router,
    private dynamicComponentLoadingService: DynamicComponentLoadingService,
  ) {
  }

  ngOnInit() {
    console.log('AppComponent ngOnInit');
    this.fetchStatusCode();
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  private fetchStatusCode() {
    this.codeStatusService.fetchStatusCode().pipe(
      take(1) // Take only the first emission from the observable
    ).subscribe({
      next: (statusCode) => {
        this.statusCode = statusCode;
        this.observeRouterEvents(); // Set up router events after status code is fetched
        this.loadDynamicComponents(); // Load dynamic components after status code is fetched
      },
      error: (error) => {
        console.error('Error fetching status code:', error);
      },
    });
  }

  private observeRouterEvents() {
    this.subscriptions.add(
      this.router.events.pipe(
        filter((event): event is NavigationEnd => event instanceof NavigationEnd)
      ).subscribe(event => {
        this.handleNavigationChange(event);
      })
    );
  }

  private loadDynamicComponents() {
    if (this.statusCode) {
      this.createComponent(this.headerContainer, ComponentLocation.Header);
      this.createComponent(this.footerContainer, ComponentLocation.Footer);
      if (this.router.url === '/') {
        this.createComponent(this.homeContainer, ComponentLocation.Home);
      }
    }
  }

  private handleNavigationChange(event: NavigationEnd) {
    const url = event.urlAfterRedirects.split('?')[0];
    console.log(`Handling navigation change for url: ${url}`);
    this.clearAllContainers();
    const directMatchAction = this.routesClearingMap.get(url);
    console.log(`handleNavigationChange Direct match action found for url: ${url}`, directMatchAction);
    if (directMatchAction) {
      console.log(`handleNavigationChange Direct match found for url: ${url}`);
      directMatchAction();
    } else if (/^\/city\/.+/.test(url)) {
      console.log(`handleNavigationChange City match found for url: ${url}`);
      this.loadDynamicInvestmentComponent(url);
    } else if (url === '/') {
      console.log('handleNavigationChange Loading home component:' + ComponentLocation.Home.valueOf());
      this.createComponent(this.homeContainer, ComponentLocation.Home);
    } else {
      // Handle other routes
    }
    console.log('handleNavigationChange Finished handling navigation change');
  }

  private clearAllContainers() {
    console.log('Clearing all containers');
    this.homeContainer.clear();
    this.contactContainer.clear();
    this.investmentListContainer.clear();
    // Clear other containers if any
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
    console.log(`Loading dynamic investment component for url: ${url}`);
    const urlSegments = url.split('/');
    const cityName = urlSegments.length > 2 ? urlSegments[2] : null;
    if (!cityName) {
      console.error('City name is not available in the URL');
      return;
    }
    const statusKey = this.statusCode?.code || 'default';
    const componentMapping = this.componentConfig[statusKey];
    const componentClass = componentMapping[ComponentLocation.InvestmentList];
    if (componentClass) {
      this.investmentListContainer.clear();
      this.dynamicComponentLoadingService.loadComponent(
        this.investmentListContainer,
        componentClass,
        {name: cityName}
      );
    } else {
      console.error(`No component found for ${ComponentLocation.InvestmentList} in status key ${statusKey}`);
    }
  }

  private createComponent(container: ViewContainerRef, location: ComponentLocation) {
    console.log(`Creating component for location: ${location}`);
    if (!this.statusCode) {
      console.warn('Status code is not available, cannot create component');
      return
    }
    ;
    const statusKey = this.statusCode.code;
    console.log(`Creating component for status key: ${statusKey}`);
    const componentConfigEntry = this.componentConfig[statusKey];
    if (!componentConfigEntry) {
      console.warn(`No component config found for status key: ${statusKey}`);
      return;
    }
    const componentClass = componentConfigEntry[location];
    if (!componentClass) {
      console.warn(`No component class found for location: ${location} and status key: ${statusKey}`);
      return;
    }
    console.log(`Preparing to load component of type : ${componentClass.name}`);
    this.dynamicComponentLoadingService.loadComponent(container, componentClass);
  }

}
