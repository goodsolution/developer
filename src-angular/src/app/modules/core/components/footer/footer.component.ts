import {Component, ViewEncapsulation} from '@angular/core';

@Component({
  selector: 'app-footer',
  template: `
    <p class="text-center">
      &copy; {{'Copy Rights' | translate}}
    </p>
  `,
  styleUrls: ['./footer.component.css'],
  encapsulation: ViewEncapsulation.Emulated
})
export class FooterComponent {


}
