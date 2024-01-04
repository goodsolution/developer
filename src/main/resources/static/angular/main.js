"use strict";(self.webpackChunksrc_angular=self.webpackChunksrc_angular||[]).push([[179],{297:(c,C,s)=>{var m=s(6593),r=s(266),t=s(5879);const U=[];let x=(()=>{class e{static#t=this.\u0275fac=function(n){return new(n||e)};static#e=this.\u0275mod=t.oAB({type:e});static#o=this.\u0275inj=t.cJS({imports:[r.Bz.forRoot(U),r.Bz]})}return e})();var a=s(9515);var u=s(9862);let F=(()=>{class e{constructor(o){this.http=o,this.apiUrl="https://localhost:8081/api/system/developers/logo"}getLogoUrl(){return this.http.get(`${this.apiUrl}`)}static#t=this.\u0275fac=function(n){return new(n||e)(t.LFG(u.eN))};static#e=this.\u0275prov=t.Yz7({token:e,factory:e.\u0275fac,providedIn:"root"})}return e})(),A=(()=>{class e{constructor(o){this.http=o,this.apiUrl="https://localhost:8081/api/system/cities"}getCities(){return this.http.get(`${this.apiUrl}`)}static#t=this.\u0275fac=function(n){return new(n||e)(t.LFG(u.eN))};static#e=this.\u0275prov=t.Yz7({token:e,factory:e.\u0275fac,providedIn:"root"})}return e})(),L=(()=>{class e{constructor(o){this.http=o,this.apiSystemCode="https://localhost:8081/api/system/code"}getDeveloperStatus(){return this.http.get(`${this.apiSystemCode}`)}static#t=this.\u0275fac=function(n){return new(n||e)(t.LFG(u.eN))};static#e=this.\u0275prov=t.Yz7({token:e,factory:e.\u0275fac,providedIn:"root"})}return e})();var g=s(6814),v=s(1274),h=s(2296),d=s(4677);function w(e,l){if(1&e&&(t.TgZ(0,"button",12),t._uU(1),t.qZA()),2&e){const o=l.$implicit;t.Q6J("routerLink","/"+o.name),t.xp6(1),t.hij(" ",o.name," ")}}function M(e,l){if(1&e){const o=t.EpF();t.ynx(0),t.TgZ(1,"nav")(2,"mat-toolbar",1),t._UZ(3,"img",2),t.TgZ(4,"button",3)(5,"i",4),t._uU(6,"location_city"),t.qZA(),t._uU(7),t.ALo(8,"translate"),t.qZA(),t.TgZ(9,"mat-menu",null,5),t.YNc(11,w,2,2,"button",6),t.qZA(),t.TgZ(12,"button",7),t._uU(13,"Contact"),t.qZA(),t._UZ(14,"span",8),t.TgZ(15,"button",3)(16,"i",4),t._uU(17,"language"),t.qZA(),t._uU(18,"Language"),t.qZA(),t.TgZ(19,"mat-menu",null,9)(21,"button",10),t.NdJ("click",function(){t.CHM(o);const i=t.oxw();return t.KtG(i.switchLanguage("pl"))}),t._uU(22,"Polish"),t.qZA(),t.TgZ(23,"button",10),t.NdJ("click",function(){t.CHM(o);const i=t.oxw();return t.KtG(i.switchLanguage("en"))}),t._uU(24,"English"),t.qZA()(),t.TgZ(25,"button",11)(26,"i",4),t._uU(27,"pin"),t.qZA(),t._uU(28,"Login"),t.qZA()()(),t.BQk()}if(2&e){const o=t.MAs(10),n=t.MAs(20),i=t.oxw();t.xp6(3),t.s9C("src",i.url,t.LSH),t.xp6(1),t.Q6J("matMenuTriggerFor",o),t.xp6(3),t.Oqu(t.lcZ(8,5,"Cities")),t.xp6(4),t.Q6J("ngForOf",i.cities),t.xp6(4),t.Q6J("matMenuTriggerFor",n)}}function J(e,l){if(1&e&&(t.TgZ(0,"button",12),t._uU(1),t.qZA()),2&e){const o=l.$implicit;t.Q6J("routerLink","/"+o.name),t.xp6(1),t.hij(" ",o.name," ")}}function S(e,l){if(1&e&&(t.ynx(0),t.TgZ(1,"nav")(2,"mat-toolbar",1),t._UZ(3,"img",2),t.TgZ(4,"button",3)(5,"i",4),t._uU(6,"location_city"),t.qZA(),t._uU(7,"Main Cities "),t.qZA(),t.TgZ(8,"mat-menu",null,5),t.YNc(10,J,2,2,"button",6),t.qZA(),t.TgZ(11,"button",13),t._uU(12,"Special Offers!"),t.qZA(),t._UZ(13,"span",8),t.TgZ(14,"button",3)(15,"i",4),t._uU(16,"language"),t.qZA(),t._uU(17,"Language"),t.qZA(),t.TgZ(18,"mat-menu",null,9)(20,"button",14),t._uU(21,"Polish"),t.qZA(),t.TgZ(22,"button",14),t._uU(23,"English"),t.qZA()(),t.TgZ(24,"button",11)(25,"i",4),t._uU(26,"pin"),t.qZA(),t._uU(27,"Login"),t.qZA()()(),t.BQk()),2&e){const o=t.MAs(9),n=t.MAs(19),i=t.oxw();t.xp6(3),t.s9C("src",i.url,t.LSH),t.xp6(1),t.Q6J("matMenuTriggerFor",o),t.xp6(6),t.Q6J("ngForOf",i.cities),t.xp6(4),t.Q6J("matMenuTriggerFor",n)}}let B=(()=>{class e{constructor(o,n,i,Z){this.service=o,this.cityService=n,this.codeService=i,this.translate=Z,this.cities=[],Z.setDefaultLang("en")}switchLanguage(o){this.translate.use(o)}getCode(){this.codeService.getDeveloperStatus().subscribe(o=>{this.code=o.code})}getLogoUrl(){this.service.getLogoUrl().subscribe(o=>{this.url=o.url})}getCities(){this.cityService.getCities().subscribe(o=>{this.cities=o.cities})}ngOnInit(){this.getCode(),this.getLogoUrl(),this.getCities()}static#t=this.\u0275fac=function(n){return new(n||e)(t.Y36(F),t.Y36(A),t.Y36(L),t.Y36(a.sK))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-header"]],decls:2,vars:2,consts:[[4,"ngIf"],["color","primary"],["alt","Logo",1,"toolbar-logo",3,"src"],["mat-button","",3,"matMenuTriggerFor"],[1,"material-icons"],["cities_menu","matMenu"],["mat-menu-item","",3,"routerLink",4,"ngFor","ngForOf"],["mat-button","","routerLink","/contact"],[1,"spacer"],["language_menu","matMenu"],["mat-menu-item","",3,"click"],["mat-button","","routerLink","/login"],["mat-menu-item","",3,"routerLink"],["mat-button","","routerLink","/offers"],["mat-menu-item",""]],template:function(n,i){1&n&&(t.YNc(0,M,29,7,"ng-container",0),t.YNc(1,S,28,4,"ng-container",0)),2&n&&(t.Q6J("ngIf","domdevelopment"===i.code),t.xp6(1),t.Q6J("ngIf","antal"===i.code))},dependencies:[g.sg,g.O5,v.Ye,h.lW,d.VK,d.OP,d.p6,r.rH,a.X$],styles:[".spacer[_ngcontent-%COMP%]{flex:1 1 auto}.toolbar-logo[_ngcontent-%COMP%]{height:100%;max-height:64px;width:auto}"]})}return e})(),H=(()=>{class e{static#t=this.\u0275fac=function(n){return new(n||e)};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-footer"]],decls:2,vars:0,consts:[[1,"text-center"]],template:function(n,i){1&n&&(t.TgZ(0,"p",0),t._uU(1," \xa9 Prawa zastrze\u017cone "),t.qZA())},styles:["p[_ngcontent-%COMP%]{width:70%;margin:30px auto 0;padding-top:15px;border-top:1px solid #c9b9b9;font-size:1.1rem}"]})}return e})(),q=(()=>{class e{constructor(o){this.translate=o,this.title="system page",o.setDefaultLang("en")}static#t=this.\u0275fac=function(n){return new(n||e)(t.Y36(a.sK))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-root"]],decls:4,vars:0,template:function(n,i){1&n&&(t._UZ(0,"app-header"),t.TgZ(1,"main"),t._UZ(2,"router-outlet"),t.qZA(),t._UZ(3,"app-footer"))},dependencies:[B,H,r.lC]})}return e})();var T=s(617),f=s(9116),b=s(2032);let z=(()=>{class e{static#t=this.\u0275fac=function(n){return new(n||e)};static#e=this.\u0275mod=t.oAB({type:e});static#o=this.\u0275inj=t.cJS({imports:[v.g0,T.Ps,h.ot,f.lN,b.c,d.Tx]})}return e})();var p=s(6223),O=s(349);let _=(()=>{class e{static#t=this.\u0275fac=function(n){return new(n||e)};static#e=this.\u0275mod=t.oAB({type:e});static#o=this.\u0275inj=t.cJS({imports:[g.ez,g.ez,z,p.u5,p.UX,O.PW]})}return e})(),N=(()=>{class e{static#t=this.\u0275fac=function(n){return new(n||e)};static#e=this.\u0275mod=t.oAB({type:e});static#o=this.\u0275inj=t.cJS({imports:[_,r.Bz,a.aw]})}return e})();const P=[{path:"",component:(()=>{class e{static#t=this.\u0275fac=function(n){return new(n||e)};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-home"]],decls:2,vars:0,template:function(n,i){1&n&&(t.TgZ(0,"p"),t._uU(1,"home works!"),t.qZA())}})}return e})()}];let Y=(()=>{class e{static#t=this.\u0275fac=function(n){return new(n||e)};static#e=this.\u0275mod=t.oAB({type:e});static#o=this.\u0275inj=t.cJS({imports:[r.Bz.forChild(P),r.Bz]})}return e})(),j=(()=>{class e{static#t=this.\u0275fac=function(n){return new(n||e)};static#e=this.\u0275mod=t.oAB({type:e});static#o=this.\u0275inj=t.cJS({imports:[_,Y]})}return e})();const k=[{path:"login",component:(()=>{class e{constructor(){this.hide=!0}onLogin(){}static#t=this.\u0275fac=function(n){return new(n||e)};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-login"]],decls:21,vars:4,consts:[[1,"login-wrapper","mt-3"],[3,"ngSubmit"],["f","ngForm"],["matInput",""],["matInput","",3,"type"],["mat-icon-button","","matSuffix","",3,"click"],[1,"d-flex","justify-content-end"],["mat-raised-button","","color","primary"]],template:function(n,i){1&n&&(t.TgZ(0,"div",0)(1,"h2"),t._uU(2,"Login"),t.qZA(),t.TgZ(3,"form",1,2),t.NdJ("ngSubmit",function(){return i.onLogin()}),t.TgZ(5,"div")(6,"mat-form-field")(7,"mat-label"),t._uU(8,"User Name"),t.qZA(),t._UZ(9,"input",3),t.qZA()(),t.TgZ(10,"div")(11,"mat-form-field")(12,"mat-label"),t._uU(13,"Password"),t.qZA(),t._UZ(14,"input",4),t.TgZ(15,"button",5),t.NdJ("click",function(){return i.hide=!i.hide}),t.TgZ(16,"mat-icon"),t._uU(17),t.qZA()()()(),t.TgZ(18,"div",6)(19,"button",7),t._uU(20,"Login"),t.qZA()()()()),2&n&&(t.xp6(14),t.Q6J("type",i.hide?"password":"text"),t.xp6(1),t.uIk("aria-label","Hide password")("aria-pressed",i.hide),t.xp6(2),t.Oqu(i.hide?"visibility_off":"visibility"))},dependencies:[T.Hw,h.lW,h.RK,f.KE,f.hX,f.R9,b.Nt,p._Y,p.JL,p.F],styles:[".login-wrapper[_ngcontent-%COMP%]{max-width:600px;margin:0 auto}"]})}return e})()}];let Q=(()=>{class e{static#t=this.\u0275fac=function(n){return new(n||e)};static#e=this.\u0275mod=t.oAB({type:e});static#o=this.\u0275inj=t.cJS({imports:[r.Bz.forChild(k),r.Bz]})}return e})(),I=(()=>{class e{static#t=this.\u0275fac=function(n){return new(n||e)};static#e=this.\u0275mod=t.oAB({type:e});static#o=this.\u0275inj=t.cJS({imports:[_,Q]})}return e})();const E=[{path:"WARSZAWA",component:(()=>{class e{constructor(o){this.service=o,this.cities=[]}getCities(){this.service.getCities().subscribe(o=>{this.cities=o.cities})}ngOnInit(){this.getCities()}static#t=this.\u0275fac=function(n){return new(n||e)(t.Y36(A))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-cities"]],decls:2,vars:0,template:function(n,i){1&n&&(t.TgZ(0,"div"),t._uU(1,"city Warsaw Welcome!"),t.qZA())}})}return e})()}];let R=(()=>{class e{static#t=this.\u0275fac=function(n){return new(n||e)};static#e=this.\u0275mod=t.oAB({type:e});static#o=this.\u0275inj=t.cJS({imports:[r.Bz.forChild(E),r.Bz]})}return e})(),X=(()=>{class e{static#t=this.\u0275fac=function(n){return new(n||e)};static#e=this.\u0275mod=t.oAB({type:e});static#o=this.\u0275inj=t.cJS({imports:[_,R]})}return e})();var D=s(5061);let K=(()=>{class e{static#t=this.\u0275fac=function(n){return new(n||e)};static#e=this.\u0275mod=t.oAB({type:e,bootstrap:[q]});static#o=this.\u0275inj=t.cJS({imports:[m.b2,j,I,X,N,x,u.JF,a.aw.forRoot({loader:{provide:a.Zw,useFactory:W,deps:[u.eN]}})]})}return e})();function W(e){return new D.w(e)}m.q6().bootstrapModule(K).catch(e=>console.error(e))}},c=>{c.O(0,[736],()=>c(c.s=297)),c.O()}]);