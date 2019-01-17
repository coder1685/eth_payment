import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, AfterViewInit {

  // Current user?
  //currentUser: any = JSON.parse( localStorage.getItem('credentials') );

  // List
  pitches = [
    { username: 'Juan', title: 'Title', description: 'Short pitch', image: 'https://canyacoin.files.wordpress.com/2018/01/canya_featured-images-out-and-about.jpg', timestamp: '1520219384' },
    { username: 'Juan', title: 'Title', description: 'Short pitch', image: 'https://canyacoin.files.wordpress.com/2018/01/canya_featured-images-out-and-about.jpg', timestamp: '1520219384' },
    { username: 'Juan', title: 'Title', description: 'Short pitch', image: 'https://canyacoin.files.wordpress.com/2018/01/canya_featured-images-out-and-about.jpg', timestamp: '1520219384' },
    { username: 'Juan', title: 'Title', description: 'Short pitch', image: 'https://canyacoin.files.wordpress.com/2018/01/canya_featured-images-out-and-about.jpg', timestamp: '1520219384' },
    { username: 'Juan', title: 'Title', description: 'Short pitch', image: 'https://canyacoin.files.wordpress.com/2018/01/canya_featured-images-out-and-about.jpg', timestamp: '1520219384' },
    { username: 'Juan', title: 'Title', description: 'Short pitch', image: 'https://canyacoin.files.wordpress.com/2018/01/canya_featured-images-out-and-about.jpg', timestamp: '1520219384' },
    { username: 'Juan', title: 'Title', description: 'Short pitch', image: 'https://canyacoin.files.wordpress.com/2018/01/canya_featured-images-out-and-about.jpg', timestamp: '1520219384' },
    { username: 'Juan', title: 'Title', description: 'Short pitch', image: 'https://canyacoin.files.wordpress.com/2018/01/canya_featured-images-out-and-about.jpg', timestamp: '1520219384' },
    { username: 'Juan', title: 'Title', description: 'Short pitch', image: 'https://canyacoin.files.wordpress.com/2018/01/canya_featured-images-out-and-about.jpg', timestamp: '1520219384' },
    { username: 'Juan', title: 'Title', description: 'Short pitch', image: 'https://canyacoin.files.wordpress.com/2018/01/canya_featured-images-out-and-about.jpg', timestamp: '1520219384' },
    { username: 'Juan', title: 'Title', description: 'Short pitch', image: 'https://canyacoin.files.wordpress.com/2018/01/canya_featured-images-out-and-about.jpg', timestamp: '1520219384' },
    { username: 'Juan', title: 'Title', description: 'Short pitch', image: 'https://canyacoin.files.wordpress.com/2018/01/canya_featured-images-out-and-about.jpg', timestamp: '1520219384' },
    { username: 'Juan', title: 'Title', description: 'Short pitch', image: 'https://canyacoin.files.wordpress.com/2018/01/canya_featured-images-out-and-about.jpg', timestamp: '1520219384' }
  ];

  // Flags
  loading = true;

  constructor(private router: Router,
    private activatedRoute:  ActivatedRoute) {

  }

  ngOnInit() {
    setTimeout( () => {
      this.loading = false;
    }, 2000 );
  }

  ngAfterViewInit() {
    this.activatedRoute.params.subscribe( (params) => {
      // PARAM? = params['query'] ? params['query'] : '';
    });
  }
}
