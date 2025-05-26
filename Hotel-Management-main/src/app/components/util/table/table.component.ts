import { Component, Input } from '@angular/core';
import { PaymentComponent } from '../../../pages/payement-service/payement-service.component'; // Adjust the path as needed
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface Item {
  [key: string]: any;
  id?: number;
  paid?: boolean;
  billNumber?: number;
  netAmount?: number | string;
}

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule,PaymentComponent], // Import CommonModule for *ngIf and PaymentServiceComponent
})
export class TableComponent {
  @Input() items: Item[] = [];
  @Input() tableHeading: string = '';
  @Input() onDelete: ((id: number) => void) | undefined;
  @Input() onUpdate?: (item: any) => void;
  @Input() config: any;
  @Input() billTable: boolean = false;

  getKeys(): string[] {
    if (this.items.length === 0) {
      return [];
    }
    return Object.keys(this.items[0]).filter((key) => key !== 'paid');
  }

  handleDelete(id: number | undefined): void {
    if (this.onDelete && id !== undefined) {
      this.onDelete(id);
    }
  }

  handleUpdate(item: Item): void {
    if (this.onUpdate) {
      this.onUpdate(item);
    }
  }

  getAmount(amount: number | string | undefined): number {
    if (amount === undefined || amount === null) {
      return 0;
    }
    const numAmount = parseFloat(amount.toString());
    return Math.round(numAmount);
  }
}