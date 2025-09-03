/**
 * https://material.angular.dev/components/snack-bar/overview
 */

import { Component, inject } from '@angular/core';
import { MatSnackBarAction, MatSnackBarActions, MatSnackBarRef } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';
import { MatIcon } from '@angular/material/icon';

type ToasterProps = {
  message: string;
};

@Component({
  selector: 'app-toaster',
  imports: [MatButtonModule, MatSnackBarActions, MatSnackBarAction, MatIcon],
  templateUrl: './toaster.html',
  styleUrl: './toaster.scss',
})
export class Toaster {
  toasterRef = inject(MatSnackBarRef);
  data = inject(MAT_SNACK_BAR_DATA) as ToasterProps;
}
