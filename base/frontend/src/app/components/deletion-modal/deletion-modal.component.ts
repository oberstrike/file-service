import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './deletion-modal.component.html',
  styleUrls: ['./deletion-modal.component.sass']
})
export class DeletionModalComponent implements OnInit {

  @Input()
  targetDomain: string | undefined

  @Input()
  hidden = false;

  @Input()
  onSuccess = () => {};

  @Input()
  onCancel = () => {};

  constructor() { }

  ngOnInit(): void {
    console.log(this.targetDomain)
  }


}
