"use strict";(self.webpackChunksrc_angular=self.webpackChunksrc_angular||[]).push([[179],{1041:(g,U,a)=>{var y=a(6593),c=a(266),t=a(5879);const q=[];let S=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[c.Bz.forRoot(q),c.Bz]})}return e})();var u=a(9862);let F=(()=>{class e{constructor(n){this.http=n,this.apiUrl="https://localhost:8081/api/system/developers/logo"}getLogoUrl(){return this.http.get(`${this.apiUrl}`)}static#t=this.\u0275fac=function(o){return new(o||e)(t.LFG(u.eN))};static#e=this.\u0275prov=t.Yz7({token:e,factory:e.\u0275fac,providedIn:"root"})}return e})();var D=a(1687),N=a(4664),J=a(2096),M=a(9397);let b=(()=>{class e{constructor(n){this.http=n,this.apiUrl="https://localhost:8081/api/system/cities",this.lastUpdated=0,this.updateInterval=6e4,this.startPeriodicUpdate()}startPeriodicUpdate(){console.log("Starting periodic update Cities 60 sec interval"),(0,D.F)(this.updateInterval).pipe((0,N.w)(()=>this.fetchDataFromAPI())).subscribe()}getCities(){return this.cache?(console.log("Fetching data from cache Cities"),(0,J.of)(this.cache)):(console.log("Fetching data from API Cities"),this.fetchDataFromAPI())}fetchDataFromAPI(){return this.http.get(`${this.apiUrl}`).pipe((0,M.b)(n=>{this.isDataUpdated(n)?(this.cache=n,this.lastUpdated=(new Date).getTime()):console.log("cities- No changes in data, cache not updated")}))}isDataUpdated(n){if(!this.cache||this.cache.cities.length!==n.cities.length)return console.log("CIty is DataUpdated Cache is empty or data length is different"),!0;const o=new Set(this.cache.cities.map(s=>s.id)),i=new Set(n.cities.map(s=>s.id));for(const s of i)if(!o.has(s))return!0;for(const s of o)if(!i.has(s))return!0;return!1}static#t=this.\u0275fac=function(o){return new(o||e)(t.LFG(u.eN))};static#e=this.\u0275prov=t.Yz7({token:e,factory:e.\u0275fac,providedIn:"root"})}return e})();var L=a(8645);let v=(()=>{class e{constructor(n){this.http=n,this.apiSystemCode="https://localhost:8081/api/system/code",this.contactComponentTrigger=new L.x}fetchStatusCode(){return this.http.get(`${this.apiSystemCode}`)}triggerContactComponentLoading(){this.contactComponentTrigger.next()}getContactComponentTrigger(){return this.contactComponentTrigger.asObservable()}static#t=this.\u0275fac=function(o){return new(o||e)(t.LFG(u.eN))};static#e=this.\u0275prov=t.Yz7({token:e,factory:e.\u0275fac,providedIn:"root"})}return e})();var p=a(9515),m=a(6814),A=a(1274),h=a(2296),d=a(3487);const Y=function(e){return["/city",e]};function B(e,r){if(1&e&&(t.TgZ(0,"button",12),t._uU(1),t.qZA()),2&e){const n=r.$implicit;t.Q6J("routerLink",t.VKq(2,Y,n.name)),t.xp6(1),t.hij(" ",n.name," ")}}const k=function(){return["/contact"]};let H=(()=>{class e{constructor(n,o,i,s){this.service=n,this.cityService=o,this.codeService=i,this.translate=s,this.cities=[],s.setDefaultLang("en")}switchLanguage(n){this.translate.use(n)}getCode(){this.codeService.fetchStatusCode().subscribe(n=>{this.code=n.code})}getLogoUrl(){this.service.getLogoUrl().subscribe(n=>{this.url=n.url})}getCities(){this.cityService.getCities().subscribe(n=>{this.cities=n.cities})}ngOnInit(){this.getCode(),this.getLogoUrl(),this.getCities()}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(F),t.Y36(b),t.Y36(v),t.Y36(p.sK))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-antal-header"]],decls:31,vars:12,consts:[["color","primary"],["alt","Logo","routerLink","",1,"toolbar-logo",3,"src"],["mat-button","",3,"matMenuTriggerFor"],[1,"material-icons"],["cities_menu","matMenu"],["mat-menu-item","",3,"routerLink",4,"ngFor","ngForOf"],["mat-button","","routerLink","/offers"],["mat-button","",3,"routerLink"],[1,"spacer"],["language_menu","matMenu"],["mat-menu-item",""],["mat-button","","routerLink","/login"],["mat-menu-item","",3,"routerLink"]],template:function(o,i){if(1&o&&(t.TgZ(0,"nav")(1,"mat-toolbar",0),t._UZ(2,"img",1),t.TgZ(3,"button",2)(4,"i",3),t._uU(5,"location_city"),t.qZA(),t._uU(6),t.ALo(7,"translate"),t.qZA(),t.TgZ(8,"mat-menu",null,4),t.YNc(10,B,2,4,"button",5),t.qZA(),t.TgZ(11,"button",6),t._uU(12,"Special Offers!"),t.qZA(),t.TgZ(13,"button",7),t._uU(14),t.ALo(15,"translate"),t.qZA(),t._UZ(16,"span",8),t.TgZ(17,"button",2)(18,"i",3),t._uU(19,"language"),t.qZA(),t._uU(20,"Language"),t.qZA(),t.TgZ(21,"mat-menu",null,9)(23,"button",10),t._uU(24,"Polish"),t.qZA(),t.TgZ(25,"button",10),t._uU(26,"English"),t.qZA()(),t.TgZ(27,"button",11)(28,"i",3),t._uU(29,"pin"),t.qZA(),t._uU(30,"Login"),t.qZA()()()),2&o){const s=t.MAs(9),O=t.MAs(22);t.xp6(2),t.s9C("src",i.url,t.LSH),t.xp6(1),t.Q6J("matMenuTriggerFor",s),t.xp6(3),t.Oqu(t.lcZ(7,7,"Main Cities")),t.xp6(4),t.Q6J("ngForOf",i.cities),t.xp6(3),t.Q6J("routerLink",t.DdM(11,k)),t.xp6(1),t.Oqu(t.lcZ(15,9,"Contact")),t.xp6(3),t.Q6J("matMenuTriggerFor",O)}},dependencies:[m.sg,A.Ye,h.lW,d.VK,d.OP,d.p6,c.rH,p.X$],styles:[".spacer[_ngcontent-%COMP%]{flex:1 1 auto}.toolbar-logo[_ngcontent-%COMP%]{height:100%;max-height:64px;width:auto}"]})}return e})();const z=function(e){return["/city",e]};function Q(e,r){if(1&e&&(t.TgZ(0,"button",11),t._uU(1),t.qZA()),2&e){const n=r.$implicit;t.Q6J("routerLink",t.VKq(2,z,n.name)),t.xp6(1),t.hij(" ",n.name," ")}}const $=function(){return["/contact"]};let j=(()=>{class e{constructor(n,o,i,s){this.service=n,this.cityService=o,this.codeService=i,this.translate=s,this.cities=[],s.setDefaultLang("en")}switchLanguage(n){this.translate.use(n)}getCode(){this.codeService.fetchStatusCode().subscribe(n=>{this.code=n.code})}getLogoUrl(){this.service.getLogoUrl().subscribe(n=>{this.url=n.url})}getCities(){this.cityService.getCities().subscribe(n=>{this.cities=n.cities})}ngOnInit(){this.getCode(),this.getLogoUrl(),this.getCities()}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(F),t.Y36(b),t.Y36(v),t.Y36(p.sK))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-dode-header"]],decls:33,vars:24,consts:[["color","primary"],["alt","Logo","routerLink","",1,"toolbar-logo",3,"src"],["mat-button","",3,"matMenuTriggerFor"],[1,"material-icons"],["cities_menu","matMenu"],["mat-menu-item","",3,"routerLink",4,"ngFor","ngForOf"],["mat-button","",3,"routerLink"],[1,"spacer"],["language_menu","matMenu"],["mat-menu-item","",3,"click"],["mat-button","","routerLink","/login"],["mat-menu-item","",3,"routerLink"]],template:function(o,i){if(1&o&&(t.TgZ(0,"nav")(1,"mat-toolbar",0),t._UZ(2,"img",1),t.TgZ(3,"button",2)(4,"i",3),t._uU(5,"location_city"),t.qZA(),t._uU(6),t.ALo(7,"translate"),t.qZA(),t.TgZ(8,"mat-menu",null,4),t.YNc(10,Q,2,4,"button",5),t.qZA(),t.TgZ(11,"button",6),t._uU(12),t.ALo(13,"translate"),t.qZA(),t._UZ(14,"span",7),t.TgZ(15,"button",2)(16,"i",3),t._uU(17,"language"),t.qZA(),t._uU(18),t.ALo(19,"translate"),t.qZA(),t.TgZ(20,"mat-menu",null,8)(22,"button",9),t.NdJ("click",function(){return i.switchLanguage("pl")}),t._uU(23),t.ALo(24,"translate"),t.qZA(),t.TgZ(25,"button",9),t.NdJ("click",function(){return i.switchLanguage("en")}),t._uU(26),t.ALo(27,"translate"),t.qZA()(),t.TgZ(28,"button",10)(29,"i",3),t._uU(30,"pin"),t.qZA(),t._uU(31),t.ALo(32,"translate"),t.qZA()()()),2&o){const s=t.MAs(9),O=t.MAs(21);t.xp6(2),t.s9C("src",i.url,t.LSH),t.xp6(1),t.Q6J("matMenuTriggerFor",s),t.xp6(3),t.Oqu(t.lcZ(7,11,"Cities")),t.xp6(4),t.Q6J("ngForOf",i.cities),t.xp6(1),t.Q6J("routerLink",t.DdM(23,$)),t.xp6(1),t.Oqu(t.lcZ(13,13,"Contact")),t.xp6(3),t.Q6J("matMenuTriggerFor",O),t.xp6(3),t.Oqu(t.lcZ(19,15,"Language")),t.xp6(5),t.Oqu(t.lcZ(24,17,"Polish")),t.xp6(3),t.Oqu(t.lcZ(27,19,"English")),t.xp6(5),t.Oqu(t.lcZ(32,21,"Login"))}},dependencies:[m.sg,A.Ye,h.lW,d.VK,d.OP,d.p6,c.rH,p.X$],styles:[".spacer[_ngcontent-%COMP%]{flex:1 1 auto}.toolbar-logo[_ngcontent-%COMP%]{height:100%;max-height:64px;width:auto}"]})}return e})(),T=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-default"]],decls:2,vars:0,template:function(o,i){1&o&&(t.TgZ(0,"p"),t._uU(1,"default works!"),t.qZA())}})}return e})(),E=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-footer-antal"]],decls:3,vars:3,consts:[[1,"text-center"]],template:function(o,i){1&o&&(t.TgZ(0,"p",0),t._uU(1),t.ALo(2,"translate"),t.qZA()),2&o&&(t.xp6(1),t.hij(" \xa9 ",t.lcZ(2,1,"Copy Rights Antal"),"\n"))},dependencies:[p.X$],styles:["p[_ngcontent-%COMP%]{width:70%;margin:30px auto 0;padding-top:15px;border-top:1px solid #c9b9b9;font-size:1.1rem}"]})}return e})(),R=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-footer-dode"]],decls:3,vars:3,consts:[[1,"text-center"]],template:function(o,i){1&o&&(t.TgZ(0,"p",0),t._uU(1),t.ALo(2,"translate"),t.qZA()),2&o&&(t.xp6(1),t.hij(" \xa9 ",t.lcZ(2,1,"Copy Rights DomDevelopment"),"\n"))},dependencies:[p.X$],styles:["p[_ngcontent-%COMP%]{width:70%;margin:30px auto 0;padding-top:15px;border-top:1px solid #c9b9b9;font-size:1.1rem}"]})}return e})(),w=(()=>{class e{constructor(n){this.http=n,this.apiUrl="https://localhost:8081/api/system/developers/code"}fetchDevelopers(){return this.http.get(`${this.apiUrl}`)}static#t=this.\u0275fac=function(o){return new(o||e)(t.LFG(u.eN))};static#e=this.\u0275prov=t.Yz7({token:e,factory:e.\u0275fac,providedIn:"root"})}return e})();var Z=a(617),l=a(5195);function X(e,r){if(1&e&&(t.TgZ(0,"mat-card",1),t._UZ(1,"mat-card-header"),t.TgZ(2,"mat-card-content")(3,"div",2)(4,"mat-icon",3),t._uU(5,"place"),t.qZA(),t.TgZ(6,"div")(7,"div"),t._uU(8),t.qZA(),t.TgZ(9,"div"),t._uU(10),t.qZA()()(),t.TgZ(11,"div",2)(12,"mat-icon",4),t._uU(13,"phone"),t.qZA(),t.TgZ(14,"div"),t._uU(15),t.qZA()(),t.TgZ(16,"div",2)(17,"mat-icon",5),t._uU(18,"fax"),t.qZA(),t.TgZ(19,"div"),t._uU(20),t.qZA()(),t.TgZ(21,"div",2)(22,"mat-icon",6),t._uU(23,"email"),t.qZA(),t.TgZ(24,"div"),t._uU(25),t.qZA()()()()),2&e){const n=r.$implicit;t.xp6(8),t.Oqu(n.name),t.xp6(2),t.xDo("",n.addressStreet," ",n.addressBuildingNumber,"/",n.addressFlatNumber,", ",n.addressPostalCode,", ",n.addressCountry,""),t.xp6(5),t.Oqu(n.telephoneNumber),t.xp6(5),t.Oqu(n.faxNumber),t.xp6(5),t.Oqu(n.email)}}let G=(()=>{class e{constructor(n){this.service=n}ngOnInit(){this.getDevelopers()}getDevelopers(){this.service.fetchDevelopers().subscribe(n=>{this.developers=n})}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(w))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-contact-antal"]],decls:1,vars:1,consts:[["class","contact-card",4,"ngFor","ngForOf"],[1,"contact-card"],[1,"contact-item"],["aria-hidden","false","aria-label","Location"],["aria-hidden","false","aria-label","Phone"],["aria-hidden","false","aria-label","Fax"],["aria-hidden","false","aria-label","Email"]],template:function(o,i){1&o&&t.YNc(0,X,26,9,"mat-card",0),2&o&&t.Q6J("ngForOf",i.developers.developers)},dependencies:[m.sg,Z.Hw,l.a8,l.dn,l.dk],styles:[".contact-card[_ngcontent-%COMP%]{width:100%;max-width:600px;margin:0 auto}.contact-card[_ngcontent-%COMP%]   .mat-card-title[_ngcontent-%COMP%]{text-align:center;margin-bottom:20px}.contact-card[_ngcontent-%COMP%]   .contact-item[_ngcontent-%COMP%]{display:flex;align-items:center;margin-bottom:10px}.contact-card[_ngcontent-%COMP%]   .contact-item[_ngcontent-%COMP%]   mat-icon[_ngcontent-%COMP%]{margin-right:8px}.contact-card[_ngcontent-%COMP%]   .contact-item[_ngcontent-%COMP%]   span[_ngcontent-%COMP%]{margin-right:4px}.contact-card[_ngcontent-%COMP%]   .contact-item[_ngcontent-%COMP%]   span[_ngcontent-%COMP%]:last-child{margin-right:0}"]})}return e})();function W(e,r){if(1&e&&(t.TgZ(0,"mat-card",2),t._UZ(1,"mat-card-header"),t.TgZ(2,"mat-card-content")(3,"div",3)(4,"mat-icon",4),t._uU(5,"place"),t.qZA(),t.TgZ(6,"div")(7,"div"),t._uU(8),t.qZA(),t.TgZ(9,"div"),t._uU(10),t.qZA()()(),t.TgZ(11,"div",3)(12,"mat-icon",5),t._uU(13,"phone"),t.qZA(),t.TgZ(14,"div"),t._uU(15),t.qZA()(),t.TgZ(16,"div",3)(17,"mat-icon",6),t._uU(18,"fax"),t.qZA(),t.TgZ(19,"div"),t._uU(20),t.qZA()(),t.TgZ(21,"div",3)(22,"mat-icon",7),t._uU(23,"email"),t.qZA(),t.TgZ(24,"div"),t._uU(25),t.qZA()()()()),2&e){const n=r.$implicit;t.xp6(8),t.Oqu(n.name),t.xp6(2),t.xDo("",n.addressStreet," ",n.addressBuildingNumber,"/",n.addressFlatNumber," , ",n.addressPostalCode,", ",n.addressCountry," "),t.xp6(5),t.Oqu(n.telephoneNumber),t.xp6(5),t.Oqu(n.faxNumber),t.xp6(5),t.Oqu(n.email)}}function K(e,r){if(1&e&&(t.TgZ(0,"div"),t.YNc(1,W,26,9,"mat-card",1),t.qZA()),2&e){const n=t.oxw();t.xp6(1),t.Q6J("ngForOf",null==n.developers?null:n.developers.developers)}}let V=(()=>{class e{constructor(n){this.service=n}ngOnInit(){this.getDevelopers()}getDevelopers(){this.service.fetchDevelopers().subscribe(n=>{this.developers=n})}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(w))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-contact-dode"]],decls:1,vars:1,consts:[[4,"ngIf"],["class","contact-card",4,"ngFor","ngForOf"],[1,"contact-card"],[1,"contact-item"],["aria-hidden","false","aria-label","Location"],["aria-hidden","false","aria-label","Phone"],["aria-hidden","false","aria-label","Fax"],["aria-hidden","false","aria-label","Email"]],template:function(o,i){1&o&&t.YNc(0,K,2,1,"div",0),2&o&&t.Q6J("ngIf",i.developers)},dependencies:[m.sg,m.O5,Z.Hw,l.a8,l.dn,l.dk],styles:[".contact-card[_ngcontent-%COMP%]{max-width:600px;margin:2em auto;border-radius:16px;box-shadow:0 8px 30px #0000001f;overflow:hidden;font-family:Roboto,sans-serif;background:#fff;transition:box-shadow .3s ease-in-out}.contact-card[_ngcontent-%COMP%]:hover{box-shadow:0 15px 40px #00000029}.mat-card-header[_ngcontent-%COMP%]{background-color:transparent;color:#333;padding:24px;border-bottom:1px solid #eeeeee}.mat-card-title[_ngcontent-%COMP%]{font-size:1.5rem;font-weight:300;color:#555;margin-bottom:.5em}.mat-card-content[_ngcontent-%COMP%]{padding:24px;color:#333;font-size:1rem;line-height:1.5}.contact-item[_ngcontent-%COMP%]{display:flex;align-items:center;margin-bottom:16px}.contact-item[_ngcontent-%COMP%]   mat-icon[_ngcontent-%COMP%]{margin-right:16px;color:#3f51b5;font-size:1.25rem}.contact-item[_ngcontent-%COMP%]   span[_ngcontent-%COMP%]{display:block}.contact-item-icon[_ngcontent-%COMP%]{width:24px;height:24px;margin-right:8px}a[_ngcontent-%COMP%]{text-decoration:none;color:#3f51b5;transition:color .3s ease}a[_ngcontent-%COMP%]:hover{color:#ff4081}@media (max-width: 768px){.contact-card[_ngcontent-%COMP%]{margin:1em;border-radius:8px}}"]})}return e})();const tt=["headerContainer"],et=["footerContainer"],nt=["contactContainer"];function ot(e,r){}function it(e,r){}function st(e,r){}let at=(()=>{class e{constructor(n,o){this.codeStatusService=n,this.router=o,this.title="app"}ngAfterViewInit(){this.codeStatusService.fetchStatusCode().subscribe(n=>{this.statusCode=n,console.log("code loadHeaderFooterDynamicComponents",this.statusCode.code),this.loadHeaderFooterDynamicComponents(this.statusCode.code)})}ngOnInit(){this.router.events.subscribe(n=>{n instanceof c.m2&&"/contact"!==n.url&&this.contactContainer.clear()}),this.codeStatusService.getContactComponentTrigger().subscribe(()=>{console.log("code loadDynamicComponents",this.statusCode.code),this.loadDynamicComponents(this.statusCode.code)})}loadHeaderFooterDynamicComponents(n){let o,i;switch(n){case"antal":o=H,i=E;break;case"domdevelopment":o=j,i=R;break;default:o=T,i=T}this.headerContainer.clear(),this.footerContainer.clear(),this.headerContainer.createComponent(o),this.footerContainer.createComponent(i)}loadDynamicComponents(n){let o;switch(n){case"antal":o=G;break;case"domdevelopment":o=V;break;default:o=T}this.contactContainer.clear(),this.contactContainer.createComponent(o)}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(v),t.Y36(c.F0))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-root"]],viewQuery:function(o,i){if(1&o&&(t.Gf(tt,7,t.s_b),t.Gf(et,7,t.s_b),t.Gf(nt,7,t.s_b)),2&o){let s;t.iGM(s=t.CRH())&&(i.headerContainer=s.first),t.iGM(s=t.CRH())&&(i.footerContainer=s.first),t.iGM(s=t.CRH())&&(i.contactContainer=s.first)}},decls:8,vars:0,consts:[["headerContainer",""],["contactContainer",""],["footerContainer",""]],template:function(o,i){1&o&&(t.YNc(0,ot,0,0,"ng-template",null,0,t.W1O),t.TgZ(2,"main"),t.YNc(3,it,0,0,"ng-template",null,1,t.W1O),t._UZ(5,"router-outlet"),t.qZA(),t.YNc(6,st,0,0,"ng-template",null,2,t.W1O))},dependencies:[c.lC]})}return e})();var x=a(9116),I=a(2032);let ct=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[A.g0,Z.Ps,h.ot,x.lN,I.c,d.Tx,l.QW]})}return e})();var C=a(6223),rt=a(349);let _=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[m.ez,m.ez,ct,C.u5,C.UX,rt.PW]})}return e})(),lt=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[_,c.Bz,p.aw]})}return e})();function mt(e,r){1&e&&(t.ynx(0),t._UZ(1,"img",1),t.BQk())}function pt(e,r){1&e&&(t.ynx(0),t._UZ(1,"img",2),t.BQk())}const ut=[{path:"",component:(()=>{class e{constructor(n){this.codeService=n}getCode(){this.codeService.fetchStatusCode().subscribe(n=>{this.code=n.code})}ngOnInit(){this.getCode()}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(v))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-home"]],decls:2,vars:2,consts:[[4,"ngIf"],["src","assets/img/domdevelopment/domdevelopment_home_page.jpg","alt","Descriptive Alt Text",1,"responsive-image"],["src","assets/img/antal/antal_home_page.jpg","alt","Descriptive Alt Text",1,"responsive-image"]],template:function(o,i){1&o&&(t.YNc(0,mt,2,0,"ng-container",0),t.YNc(1,pt,2,0,"ng-container",0)),2&o&&(t.Q6J("ngIf","domdevelopment"===i.code),t.xp6(1),t.Q6J("ngIf","antal"===i.code))},dependencies:[m.O5],styles:[".responsive-image[_ngcontent-%COMP%]{max-width:100%;height:auto;display:block;margin:20px auto 0}"]})}return e})()}];let dt=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[c.Bz.forChild(ut),c.Bz]})}return e})(),ht=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[_,dt]})}return e})();const gt=[{path:"login",component:(()=>{class e{constructor(){this.hide=!0}onLogin(){}static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-login"]],decls:21,vars:4,consts:[[1,"login-wrapper","mt-3"],[3,"ngSubmit"],["f","ngForm"],["matInput",""],["matInput","",3,"type"],["mat-icon-button","","matSuffix","",3,"click"],[1,"d-flex","justify-content-end"],["mat-raised-button","","color","primary"]],template:function(o,i){1&o&&(t.TgZ(0,"div",0)(1,"h2"),t._uU(2,"Login"),t.qZA(),t.TgZ(3,"form",1,2),t.NdJ("ngSubmit",function(){return i.onLogin()}),t.TgZ(5,"div")(6,"mat-form-field")(7,"mat-label"),t._uU(8,"User Name"),t.qZA(),t._UZ(9,"input",3),t.qZA()(),t.TgZ(10,"div")(11,"mat-form-field")(12,"mat-label"),t._uU(13,"Password"),t.qZA(),t._UZ(14,"input",4),t.TgZ(15,"button",5),t.NdJ("click",function(){return i.hide=!i.hide}),t.TgZ(16,"mat-icon"),t._uU(17),t.qZA()()()(),t.TgZ(18,"div",6)(19,"button",7),t._uU(20,"Login"),t.qZA()()()()),2&o&&(t.xp6(14),t.Q6J("type",i.hide?"password":"text"),t.xp6(1),t.uIk("aria-label","Hide password")("aria-pressed",i.hide),t.xp6(2),t.Oqu(i.hide?"visibility_off":"visibility"))},dependencies:[Z.Hw,h.lW,h.RK,x.KE,x.hX,x.R9,I.Nt,C._Y,C.JL,C.F],styles:[".login-wrapper[_ngcontent-%COMP%]{max-width:600px;margin:0 auto}"]})}return e})()}];let ft=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[c.Bz.forChild(gt),c.Bz]})}return e})(),vt=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[_,ft]})}return e})();var Ct=a(5061),P=a(9773),_t=a(940),yt=a(6306),Zt=a(8504);let xt=(()=>{class e{constructor(n){this.http=n,this.apiUrl="https://localhost:8081/api/system/investments",this.investmentCache$=null,this.cacheExpirationMs=1e5,this.lastRequestTime=null}getInvestments(){const n=(new Date).getTime();return this.investmentCache$&&this.lastRequestTime&&n-this.lastRequestTime<this.cacheExpirationMs?(console.log("Fetching data from cache Investments"),this.investmentCache$):(console.log("Fetching data from API Investments"),this.investmentCache$=this.http.get(this.apiUrl).pipe((0,M.b)(()=>this.lastRequestTime=(new Date).getTime()),(0,_t.d)(1),(0,yt.K)(o=>(console.error("Error fetching investments data:",o),(0,Zt._)(()=>o)))),this.investmentCache$)}static#t=this.\u0275fac=function(o){return new(o||e)(t.LFG(u.eN))};static#e=this.\u0275prov=t.Yz7({token:e,factory:e.\u0275fac,providedIn:"root"})}return e})(),bt=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-investment"]],inputs:{investment:"investment"},decls:14,vars:3,consts:[[1,"investment-card"],["mat-card-avatar","",1,"investment-image"],["mat-card-image","","src","assets/img/domdevelopment/domdevelopment_home_page.jpg","alt","Osiedle Harmonia"],["mat-button",""]],template:function(o,i){1&o&&(t.TgZ(0,"mat-card",0)(1,"mat-card-header"),t._UZ(2,"div",1),t.TgZ(3,"mat-card-title"),t._uU(4),t.qZA(),t.TgZ(5,"mat-card-subtitle"),t._uU(6),t.qZA()(),t._UZ(7,"img",2),t.TgZ(8,"mat-card-content")(9,"p"),t._uU(10),t.qZA()(),t.TgZ(11,"mat-card-actions")(12,"button",3),t._uU(13,"VIEW DETAILS"),t.qZA()()()),2&o&&(t.xp6(4),t.Oqu(i.investment.name),t.xp6(2),t.Oqu(i.investment.addressStreet),t.xp6(4),t.Oqu(i.investment.description))},dependencies:[h.lW,l.a8,l.hq,l.kc,l.dn,l.dk,l.G2,l.$j,l.n5],styles:[".investment-card[_ngcontent-%COMP%]{max-width:400px;margin:20px auto}.investment-image[_ngcontent-%COMP%]{background-size:cover;width:40px;height:40px}"]})}return e})();function At(e,r){if(1&e&&(t.ynx(0),t._UZ(1,"app-investment",4),t.BQk()),2&e){const n=r.$implicit;t.xp6(1),t.Q6J("investment",n)}}function Tt(e,r){if(1&e&&(t.TgZ(0,"div",2),t.YNc(1,At,2,1,"ng-container",3),t.qZA()),2&e){const n=t.oxw();t.xp6(1),t.Q6J("ngForOf",n.investments)}}function Ot(e,r){1&e&&(t.TgZ(0,"h5"),t._uU(1,"Aktualnie brak inwestycji"),t.qZA())}const Ut=[{path:"city/:name",component:(()=>{class e{constructor(n,o,i){this.service=n,this.route=o,this.cityService=i,this.allInvestments=[],this.investments=[],this.cities=[],this.cityName="",this.unsubscribe$=new L.x}getInvestments(){this.service.getInvestments().pipe((0,P.R)(this.unsubscribe$)).subscribe({next:n=>{this.allInvestments=n.investments,this.investments=n.investments,this.filterInvestmentsByCity(this.cityName)},error:n=>console.error("Error fetching investments:",n)})}getCities(){this.cityService.getCities().pipe((0,P.R)(this.unsubscribe$)).subscribe({next:n=>this.cities=n.cities,error:n=>console.error("Error fetching cities:",n)})}ngOnInit(){this.getCities(),this.getInvestments(),this.route.paramMap.subscribe(n=>{this.cityName=n.get("name")??"",this.allInvestments.length>0&&this.filterInvestmentsByCity(this.cityName)})}filterInvestmentsByCity(n){if(!n)return this.investments=[...this.allInvestments],void console.log("No city selected, copy all investments");const o=this.cities.find(i=>i.name===n);o?(this.investments=this.allInvestments.filter(i=>i.cityId===o.id),console.log(`Found ${this.investments.length} investments for city ${n}`)):(this.investments=[...this.allInvestments],console.log("No matching city found, displaying all investments"))}ngOnDestroy(){this.unsubscribe$.next(),this.unsubscribe$.complete()}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(xt),t.Y36(c.gz),t.Y36(b))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-investment-list"]],decls:2,vars:2,consts:[["class","card-container",4,"ngIf"],[4,"ngIf"],[1,"card-container"],[4,"ngFor","ngForOf"],[3,"investment"]],template:function(o,i){1&o&&(t.YNc(0,Tt,2,1,"div",0),t.YNc(1,Ot,2,0,"h5",1)),2&o&&(t.Q6J("ngIf",i.investments.length>0),t.xp6(1),t.Q6J("ngIf",0===i.investments.length))},dependencies:[m.sg,m.O5,bt],styles:[".card-container[_ngcontent-%COMP%]{justify-content:center;display:grid;grid-template-columns:repeat(auto-fill,minmax(250px,1fr));gap:10px;max-width:1200px;margin:0 auto}@media (max-width: 768px){.card-container[_ngcontent-%COMP%]{grid-template-columns:1fr}}"]})}return e})()}];let Ft=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[c.Bz.forChild(Ut),c.Bz]})}return e})(),Mt=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[m.ez,Ft,_]})}return e})();const Lt=[{path:"contact",component:(()=>{class e{constructor(n){this.codeStatus=n}ngOnInit(){this.codeStatus.triggerContactComponentLoading()}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(v))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-contact"]],decls:0,vars:0,template:function(o,i){}})}return e})()}];let wt=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[c.Bz.forChild(Lt),c.Bz]})}return e})(),It=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[m.ez,_,wt]})}return e})(),Pt=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e,bootstrap:[at]});static#n=this.\u0275inj=t.cJS({imports:[y.b2,ht,It,vt,lt,Mt,S,u.JF,p.aw.forRoot({loader:{provide:p.Zw,useFactory:qt,deps:[u.eN]}})]})}return e})();function qt(e){return new Ct.w(e)}y.q6().bootstrapModule(Pt).catch(e=>console.error(e))}},g=>{g.O(0,[736],()=>g(g.s=1041)),g.O()}]);