import {Component} from '@angular/core';
import {Router, Routes} from '@angular/router';

@Component({
  moduleId: module.id.toString(),
  selector: 'welcome',
  templateUrl: 'welcome.component.html'
})

export class WelcomeComponent {
  constructor(private router: Router) {
    this.router.navigate(['welcome']);
  }
}
