import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-menu-form',
  imports: [CommonModule],
  templateUrl: './menu-form.component.html',
})
export class MenuFormComponent {
  menuItems = [
    { id: 1, name: 'Item 1', price: 10.99 },
    { id: 2, name: 'Item 2', price: 8.99 },
    { id: 3, name: 'Item 3', price: 12.99 }
  ];

  @Output() checkboxChange = new EventEmitter<any>();

  handleCheckboxChange(item: any) {
    this.checkboxChange.emit(item);
  }
}
