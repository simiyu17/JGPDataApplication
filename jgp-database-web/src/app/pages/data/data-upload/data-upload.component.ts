import { Component } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClient, HttpEventType  } from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { GlobalService } from '@services/global.service';

@Component({
  selector: 'app-data-upload',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule, MatButtonModule, MatInputModule, MatProgressSpinnerModule],
  templateUrl: './data-upload.component.html',
  styleUrl: './data-upload.component.scss'
})
export class DataUploadComponent {

  file: any;
  uploadProgress: number | null = null;

  constructor(private httpClient: HttpClient, private gs: GlobalService) { }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
  }

  uploadExcel(){
    console.log(this.file)
    const formData = new FormData();
    formData.append('excelFile', this.file, this.file.name);
    this.httpClient.post(`${this.gs.BASE_API_URL}/bmos/upload-template`, formData, {
      reportProgress: true,
      observe: 'events'
    }).subscribe({
      next (response) {
        if (response.type === HttpEventType.UploadProgress) {
          this.uploadProgress = Math.round((100 * response.loaded) / (response.total || 1));
        } else if (response.type === HttpEventType.Response) {
          alert('File uploaded successfully!');
          this.uploadProgress = null;
        }
        console.log(response)
      },
      error(error){
        console.log(error)

      }
    });

  }
}
