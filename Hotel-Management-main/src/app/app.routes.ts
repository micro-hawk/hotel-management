import { Routes } from '@angular/router';
import { LoginComponent } from './components/authentication/login/login.component';
import { RegistrationComponent } from './components/authentication/registration/registration.component';
import { DashboardComponent } from './pages/dashboard/dashboard/dashboard.component';
import { DepartmentComponent } from './pages/department/department/department.component';
import { InventoryItemComponent } from './pages/inventory/inventory-item/inventory-item.component';
import { InventoryStaffComponent } from './pages/inventory/inventory-staff/inventory-staff.component';
import { InventoryRoomComponent } from './pages/inventory/inventory-room/inventory-room.component';
import { ReservationComponent } from './pages/reservation/reservation.component';
import { RoomServiceComponent } from './pages/room-service/room-service.component';


export const routes: Routes = [
    {path: '', redirectTo: 'login', pathMatch: 'full'},
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegistrationComponent},
    { 
        path: 'dashboard', 
        component: DashboardComponent
    },
    { 
        path: 'department', 
        component: DepartmentComponent
    },
    { 
        path: 'inventory/item', 
        component: InventoryItemComponent
    },
    { 
        path: 'inventory/staff', 
        component: InventoryStaffComponent
    },
    { 
        path: 'inventory/room', 
        component: InventoryRoomComponent
    },
    { 
        path: 'reservation', 
        component: ReservationComponent
    },
    { 
        path: 'room-service', 
        component: RoomServiceComponent
    },
    {path: '**', redirectTo: 'login'}
];
