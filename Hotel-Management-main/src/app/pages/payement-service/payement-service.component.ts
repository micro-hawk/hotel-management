import { Component, Input } from '@angular/core';
import { PaymentService } from '../../services/payement.service';
declare var Razorpay: any;


@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [],
  templateUrl: './payement-service.component.html',
  styleUrl: './payement-service.component.css'
})
export class PaymentComponent {
  @Input() amount!: number;
  @Input() userName!: string;
  @Input() email!: string;
  @Input() contact!: string;
  @Input() billId!: number | string;


  constructor(private paymentService: PaymentService) {}

  payNow() {
    this.paymentService.getPaymentOrderId(this.amount).subscribe({
      next: (response) => {
        const options = {
          key: 'rzp_test_Uj39upN4G9uX5p', // Replace with your Razorpay key
          amount: this.amount * 100,
          currency: 'INR',
          name: this.userName,
          description: `Bill No: ${this.billId}`,
          order_id: response.id, // Razorpay order ID from backend
          handler: (res: any) => {
            console.log('Payment success', res);
            alert('Payment successful!');
          },
          prefill: {
            name: this.userName,
            email: this.email,
            contact: this.contact
          },
          theme: {
            color: '#3399cc'
          }
        };

        const razorpay = new Razorpay(options);
        razorpay.open();
      },
      error: (err) => {
        console.error('Error creating Razorpay order', err);
        alert('Payment failed to start.');
      }
    });
  }
}
