import {AfterViewInit, Component} from '@angular/core';

import '../assets/app.css';
import {Router} from "@angular/router";

@Component({
  moduleId: module.id.toString(),
  selector: 'app-root',
  templateUrl: 'app.component.html'
})

export class AppComponent implements AfterViewInit{
  ngAfterViewInit(): void {
   // this.router.navigate(['welcome']);
  }

  constructor(private router: Router) {
    //this.router.navigate(['welcome']);
  }
}


