import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { InventoryService } from '../../../services/inventory.service'; 
import { TableComponent } from '../../../components/util/table/table.component'; 
import { NavbarComponent } from '../../../components/util/navbar/navbar.component'; 
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface InventoryItemModel {
  id: number;
  name: string;
  description: string;
  quantity: number;
  price: number;
}

@Component({
  selector: 'app-inventory-item',
  templateUrl: './inventory-item.component.html',
  styleUrls: ['./inventory-item.component.css'],
  standalone: true,
imports: [CommonModule, FormsModule, TableComponent,NavbarComponent]
,
})
export class InventoryItemComponent implements OnInit {
  items: InventoryItemModel[] = [];
  isUpdating = false;
  updatingId: number | null = null;
  itemName = '';
  itemDescription = '';
  itemQuantity: number | null = null;
  itemPrice: number | null = null;

  constructor(
    private router: Router,
    private inventoryService: InventoryService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.fetchItems();
  }

  fetchItems(): void {
    this.inventoryService.getItems().subscribe({
      next: (response) => {
        console.log('Items----------------', response.data);
        this.items = [...response.data];
      },
      error: (error) => {
        // this.router.navigate(['/']);
        console.log('inside fetch------- Items ', error);
      },
    });
  }

  handleSubmit(form: NgForm): void {
    if (form.valid) {
      const newItem = {
        name: this.itemName,
        description: this.itemDescription,
        quantity: this.itemQuantity !== null ? this.itemQuantity : 0,
        price: this.itemPrice !== null ? this.itemPrice : 0,
      };

      if (this.isUpdating && this.updatingId) {
        this.inventoryService.updateItem(newItem, this.updatingId).subscribe({
          next: (response) => {
            if (response.data.success) {
              this.fetchItems();
              this.toastr.success(response.data.message);
              this.resetForm();
              this.isUpdating = false;
              this.updatingId = null;
            } else {
              this.toastr.error(response.data.message);
            }
            console.log(response.headers);
          },
          error: (error) => {
            this.router.navigate(['/']);
            console.log(error);
            this.isUpdating = false;
            this.updatingId = null;
          },
        });
      } else {
        this.inventoryService.addItem(newItem).subscribe({
          next: (response) => {
            if (response.data.success) {
              this.fetchItems();
              this.toastr.success(response.data.message);
              this.resetForm();
            } else {
              this.toastr.error(response.data.message);
            }
            console.log(response.headers);
          },
          error: (error) => {
            this.router.navigate(['/']);
            console.log(error);
          },
        });
      }
    }
  }

  handleDelete(id: number): void {
    this.inventoryService.deleteItem(id).subscribe({
      next: (response) => {
        if (response.data.success) {
          this.fetchItems();
          this.toastr.success(response.data.message);
        } else {
          this.toastr.error(response.data.message);
        }
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  handleUpdate(item: InventoryItemModel): void {
    console.log('---------update Item ---', item.id);
    this.itemName = item.name;
    this.itemDescription = item.description;
    this.itemQuantity = item.quantity;
    this.itemPrice = item.price;
    this.isUpdating = true;
    this.updatingId = item.id;
  }

  resetForm(): void {
    this.itemName = '';
    this.itemDescription = '';
    this.itemQuantity = null;
    this.itemPrice = null;
  }
}