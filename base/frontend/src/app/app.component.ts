import {Component} from '@angular/core';
import {DeletionModalComponent} from "./components/deletion-modal/deletion-modal.component";
import {animate, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {

  domainManagement: DomainManagementService = new DomainManagementServiceImpl();

  domainGroupedByFirstLetter: DomainGroupedByFirstLetter[] = [];

  title = 'frontend';

  component = DeletionModalComponent;

  componentInputs = {
    targetDomain: '',
    hidden: true,
    onSuccess: () => {
      this.componentInputs.hidden = !this.componentInputs.hidden;
      this.domainManagement.delete(this.componentInputs.targetDomain);
    },
    onCancel: () => {
      console.log('Cancel');
      this.componentInputs.hidden = !this.componentInputs.hidden;
    }
  }

  ngOnInit() {
    for (let i = 0; i < 100; i++) {
      let next = `${btoa(Math.random().toString()).substr(10, 5)}`;
      this.domainManagement.addDomain(next);
    }
    this.domainGroupedByFirstLetter = this.domainManagement.domainGroupedByFirstLetters;
  }

  delete(domain: string) {
    this.componentInputs.targetDomain = domain;
    this.componentInputs.hidden = false;
  }

  onSearch(event: any) {
    const value = event.target.value;
    //if value is a valid string and has more then 3 characters then filter the domains
    if (typeof value === 'string' && value.length > 3 || value.length === 0) {
      this.domainGroupedByFirstLetter = this.domainManagement.filter(value);
    }
  }


  onFileSelected(event: any) {
    let eventTarget = event.target;
    if(eventTarget == null)
      return;
    if(eventTarget.files == null)
      return;
    if(eventTarget.files.length == 0)
      return;

    const file: File = eventTarget.files[0];

    if(file){
      const fileName = file.name;
      console.log(fileName);
    }

  }
}

class DomainManagementServiceImpl implements DomainManagementService {

  delete(targetDomain: string) {
    this.domainGroupedByFirstLetters.forEach(group => {
      group.domains.forEach(domain => {
        if (domain === targetDomain) {
          group.domains.splice(group.domains.indexOf(domain), 1);
        }
      });
    });
  }

  //a filter function that returns a array of DomainGroupedByFirstLetter
  filter(filter: string): DomainGroupedByFirstLetter[] {
    if (filter.length === 0) {
      return this.domainGroupedByFirstLetters;
    }
    //log the filter
    console.log('filter ' + filter);
    return this.domainGroupedByFirstLetters.filter(domain =>
      domain.domains.filter(x =>
        x.toLowerCase().startsWith(filter)));
  }

  addDomain(domain: string) {
    //get first letter of domain to uppercast
    const firstLetter = domain.charAt(0).toUpperCase();
    let exists = false;

    this.domainGroupedByFirstLetters.forEach(
      (value, key) => {
        if (value.letter === firstLetter) {
          value.domains.push(domain);
          exists = true;
        }
      }
    );

    if (!exists) {
      this.domainGroupedByFirstLetters.push({letter: firstLetter, domains: [domain]});
    }

    //sort the domains by letter
    this.domainGroupedByFirstLetters.sort((a, b) => {
      if (a.letter < b.letter) {
        return -1;
      }
      if (a.letter > b.letter) {
        return 1;
      }
      return 0;
    });

  }

  domainGroupedByFirstLetters: DomainGroupedByFirstLetter[] = [];
}

interface DomainManagementService {
  domainGroupedByFirstLetters: DomainGroupedByFirstLetter[]

  addDomain(domain: string): void;

  filter(filter: string): DomainGroupedByFirstLetter[];

  delete(domain: string): void;
}

interface DomainGroupedByFirstLetter {
  letter: string;
  domains: string[];
}

