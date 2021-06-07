import { Component, OnInit } from '@angular/core';
import { ComprovanteService } from './comprovante.service';
import {NgZone, ViewChild} from '@angular/core';



@Component({
  selector: 'app-comprovante',
  templateUrl: './comprovante.component.html',
  styleUrls: ['./comprovante.component.css']
})
export class ComprovanteComponent implements OnInit {

  mensagemCabecalho: string = ''
  inputComprovante: string = ''
  inputTemplate: string = ''
  timestampEnvio: any
  timestampRetorno: any
  timestampTempo: any
  template : any
  templateTratado : any
  comprovante : any


  constructor(private comprovanteService: ComprovanteService) {
    this.mensagemCabecalho = "Insira abaixo os dados do comprovante"
   }

  ngOnInit(): void {
  }

  limpar(){
    this.inputComprovante = ''
    this.inputTemplate = ''
  }

  tratarTemplate(){
    
    if(this.inputTemplate == '' || this.inputComprovante == '')
      return

    this.comprovante = JSON.parse(this.inputComprovante);
    this.template = JSON.parse(this.inputTemplate);

    let comprovanteTemplate : any = {
      comprovante : JSON.parse( this.inputComprovante).comprovante,
      template : JSON.parse( this.inputTemplate).template
    }

    var comprovante_ = JSON.stringify(  comprovanteTemplate );
    
    //console.log(comprovante_)
    
    this.timestampEnvio = new Date()

    this.comprovanteService.postCarById(comprovante_).subscribe(
      (retorno) => {
        this.timestampRetorno = new Date()
        this.timestampTempo = this.timestampRetorno - this.timestampEnvio
        this.templateTratado=retorno;

      }
    )
  }



}
