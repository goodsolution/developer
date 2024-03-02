import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {DynamicComponentLoadingService} from "../core/services/dynamic-component-loading.service";
import {ActivatedRoute} from "@angular/router";

// @ts-ignore
@Component({
  selector: 'app-premise-detail',
  template: '', // No HTML content, it's a dynamic loader only
  styleUrls: ['./premise-detail.component.scss']
})
export class PremiseDetailComponent implements OnInit, OnDestroy {

  private premiseId = '';
  private routeSub!: Subscription;

  constructor(
    private dynamicLoadingService: DynamicComponentLoadingService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.routeSub = this.route.paramMap.subscribe(params => {
      const id: string | null = params.get('id');
      this.handlePremiseIdChange(id);
    });
  }

  ngOnDestroy(): void {
    if (this.routeSub) {
      this.routeSub.unsubscribe();
    }
  }

  private handlePremiseIdChange(id: string | null) {
    if (id && id !== this.premiseId) {
      this.premiseId = id;
      this.dynamicLoadingService.triggerPremiseDetailComponentLoading({premiseId: this.premiseId});
    }
  }

}
