import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-carousel',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './carousel.component.html',
  styleUrl: './carousel.component.css'
})
export class CarouselComponent implements OnInit {
  @Input() slides: { imageUrl: string; title?: string; description?: string }[] = [
    { imageUrl: 'https://placehold.co/800x400/EEE/31343C', title: 'First slide label', description: 'Some representative placeholder content for the first slide.' },
    { imageUrl: 'https://placehold.co/800x400/EEE/31343C', title: 'Second slide label', description: 'Some representative placeholder content for the second slide.' },
    { imageUrl: 'https://placehold.co/800x400/EEE/31343C', title: 'Third slide label', description: 'Some representative placeholder content for the third slide.' },
  ]; // Default data

  @Input() interval = 10000; // Default interval

  constructor() { }

  ngOnInit(): void {
    // You could potentially fetch slide data here, or leave it as an input
  }

}
