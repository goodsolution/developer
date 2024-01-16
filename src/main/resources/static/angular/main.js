"use strict";(self.webpackChunksrc_angular=self.webpackChunksrc_angular||[]).push([[179],{390:(g,U,s)=>{var _=s(6593),c=s(266),t=s(5879);const P=[];let q=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[c.Bz.forRoot(P),c.Bz]})}return e})();var u=s(9862);let O=(()=>{class e{constructor(n){this.http=n,this.apiUrl="https://localhost:8081/api/system/developers/logo"}getLogoUrl(){return this.http.get(`${this.apiUrl}`)}static#t=this.\u0275fac=function(o){return new(o||e)(t.LFG(u.eN))};static#e=this.\u0275prov=t.Yz7({token:e,factory:e.\u0275fac,providedIn:"root"})}return e})();var S=s(1687),D=s(4664),J=s(2096),F=s(9397);let b=(()=>{class e{constructor(n){this.http=n,this.apiUrl="https://localhost:8081/api/system/cities",this.lastUpdated=0,this.updateInterval=6e4,this.startPeriodicUpdate()}startPeriodicUpdate(){console.log("Starting periodic update Cities 60 sec interval"),(0,S.F)(this.updateInterval).pipe((0,D.w)(()=>this.fetchDataFromAPI())).subscribe()}getCities(){return this.cache?(console.log("Fetching data from cache Cities"),(0,J.of)(this.cache)):(console.log("Fetching data from API Cities"),this.fetchDataFromAPI())}fetchDataFromAPI(){return this.http.get(`${this.apiUrl}`).pipe((0,F.b)(n=>{this.isDataUpdated(n)?(this.cache=n,this.lastUpdated=(new Date).getTime()):console.log("cities- No changes in data, cache not updated")}))}isDataUpdated(n){if(!this.cache||this.cache.cities.length!==n.cities.length)return console.log("CIty is DataUpdated Cache is empty or data length is different"),!0;const o=new Set(this.cache.cities.map(a=>a.id)),i=new Set(n.cities.map(a=>a.id));for(const a of i)if(!o.has(a))return!0;for(const a of o)if(!i.has(a))return!0;return!1}static#t=this.\u0275fac=function(o){return new(o||e)(t.LFG(u.eN))};static#e=this.\u0275prov=t.Yz7({token:e,factory:e.\u0275fac,providedIn:"root"})}return e})(),y=(()=>{class e{constructor(n){this.http=n,this.apiSystemCode="https://localhost:8081/api/system/code"}fetchStatusCode(){return this.http.get(`${this.apiSystemCode}`)}static#t=this.\u0275fac=function(o){return new(o||e)(t.LFG(u.eN))};static#e=this.\u0275prov=t.Yz7({token:e,factory:e.\u0275fac,providedIn:"root"})}return e})();var p=s(9515),m=s(6814),A=s(1274),h=s(2296),d=s(3487);const N=function(e){return["/city",e]};function B(e,l){if(1&e&&(t.TgZ(0,"button",12),t._uU(1),t.qZA()),2&e){const n=l.$implicit;t.Q6J("routerLink",t.VKq(2,N,n.name)),t.xp6(1),t.hij(" ",n.name," ")}}const Y=function(e){return["/contact",e]};let k=(()=>{class e{constructor(n,o,i,a){this.service=n,this.cityService=o,this.codeService=i,this.translate=a,this.cities=[],a.setDefaultLang("en")}switchLanguage(n){this.translate.use(n)}getCode(){this.codeService.fetchStatusCode().subscribe(n=>{this.code=n.code})}getLogoUrl(){this.service.getLogoUrl().subscribe(n=>{this.url=n.url})}getCities(){this.cityService.getCities().subscribe(n=>{this.cities=n.cities})}ngOnInit(){this.getCode(),this.getLogoUrl(),this.getCities()}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(O),t.Y36(b),t.Y36(y),t.Y36(p.sK))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-antal-header"]],decls:31,vars:13,consts:[["color","primary"],["alt","Logo","routerLink","",1,"toolbar-logo",3,"src"],["mat-button","",3,"matMenuTriggerFor"],[1,"material-icons"],["cities_menu","matMenu"],["mat-menu-item","",3,"routerLink",4,"ngFor","ngForOf"],["mat-button","","routerLink","/offers"],["mat-button","",3,"routerLink"],[1,"spacer"],["language_menu","matMenu"],["mat-menu-item",""],["mat-button","","routerLink","/login"],["mat-menu-item","",3,"routerLink"]],template:function(o,i){if(1&o&&(t.TgZ(0,"nav")(1,"mat-toolbar",0),t._UZ(2,"img",1),t.TgZ(3,"button",2)(4,"i",3),t._uU(5,"location_city"),t.qZA(),t._uU(6),t.ALo(7,"translate"),t.qZA(),t.TgZ(8,"mat-menu",null,4),t.YNc(10,B,2,4,"button",5),t.qZA(),t.TgZ(11,"button",6),t._uU(12,"Special Offers!"),t.qZA(),t.TgZ(13,"button",7),t._uU(14),t.ALo(15,"translate"),t.qZA(),t._UZ(16,"span",8),t.TgZ(17,"button",2)(18,"i",3),t._uU(19,"language"),t.qZA(),t._uU(20,"Language"),t.qZA(),t.TgZ(21,"mat-menu",null,9)(23,"button",10),t._uU(24,"Polish"),t.qZA(),t.TgZ(25,"button",10),t._uU(26,"English"),t.qZA()(),t.TgZ(27,"button",11)(28,"i",3),t._uU(29,"pin"),t.qZA(),t._uU(30,"Login"),t.qZA()()()),2&o){const a=t.MAs(9),T=t.MAs(22);t.xp6(2),t.s9C("src",i.url,t.LSH),t.xp6(1),t.Q6J("matMenuTriggerFor",a),t.xp6(3),t.Oqu(t.lcZ(7,7,"Main Cities")),t.xp6(4),t.Q6J("ngForOf",i.cities),t.xp6(3),t.Q6J("routerLink",t.VKq(11,Y,i.code)),t.xp6(1),t.Oqu(t.lcZ(15,9,"Contact")),t.xp6(3),t.Q6J("matMenuTriggerFor",T)}},dependencies:[m.sg,A.Ye,h.lW,d.VK,d.OP,d.p6,c.rH,p.X$],styles:[".spacer[_ngcontent-%COMP%]{flex:1 1 auto}.toolbar-logo[_ngcontent-%COMP%]{height:100%;max-height:64px;width:auto}"]})}return e})();const z=function(e){return["/city",e]};function H(e,l){if(1&e&&(t.TgZ(0,"button",11),t._uU(1),t.qZA()),2&e){const n=l.$implicit;t.Q6J("routerLink",t.VKq(2,z,n.name)),t.xp6(1),t.hij(" ",n.name," ")}}const $=function(e){return["/contact",e]};let j=(()=>{class e{constructor(n,o,i,a){this.service=n,this.cityService=o,this.codeService=i,this.translate=a,this.cities=[],a.setDefaultLang("en")}switchLanguage(n){this.translate.use(n)}getCode(){this.codeService.fetchStatusCode().subscribe(n=>{this.code=n.code})}getLogoUrl(){this.service.getLogoUrl().subscribe(n=>{this.url=n.url})}getCities(){this.cityService.getCities().subscribe(n=>{this.cities=n.cities})}ngOnInit(){this.getCode(),this.getLogoUrl(),this.getCities()}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(O),t.Y36(b),t.Y36(y),t.Y36(p.sK))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-dode-header"]],decls:33,vars:25,consts:[["color","primary"],["alt","Logo","routerLink","",1,"toolbar-logo",3,"src"],["mat-button","",3,"matMenuTriggerFor"],[1,"material-icons"],["cities_menu","matMenu"],["mat-menu-item","",3,"routerLink",4,"ngFor","ngForOf"],["mat-button","",3,"routerLink"],[1,"spacer"],["language_menu","matMenu"],["mat-menu-item","",3,"click"],["mat-button","","routerLink","/login"],["mat-menu-item","",3,"routerLink"]],template:function(o,i){if(1&o&&(t.TgZ(0,"nav")(1,"mat-toolbar",0),t._UZ(2,"img",1),t.TgZ(3,"button",2)(4,"i",3),t._uU(5,"location_city"),t.qZA(),t._uU(6),t.ALo(7,"translate"),t.qZA(),t.TgZ(8,"mat-menu",null,4),t.YNc(10,H,2,4,"button",5),t.qZA(),t.TgZ(11,"button",6),t._uU(12),t.ALo(13,"translate"),t.qZA(),t._UZ(14,"span",7),t.TgZ(15,"button",2)(16,"i",3),t._uU(17,"language"),t.qZA(),t._uU(18),t.ALo(19,"translate"),t.qZA(),t.TgZ(20,"mat-menu",null,8)(22,"button",9),t.NdJ("click",function(){return i.switchLanguage("pl")}),t._uU(23),t.ALo(24,"translate"),t.qZA(),t.TgZ(25,"button",9),t.NdJ("click",function(){return i.switchLanguage("en")}),t._uU(26),t.ALo(27,"translate"),t.qZA()(),t.TgZ(28,"button",10)(29,"i",3),t._uU(30,"pin"),t.qZA(),t._uU(31),t.ALo(32,"translate"),t.qZA()()()),2&o){const a=t.MAs(9),T=t.MAs(21);t.xp6(2),t.s9C("src",i.url,t.LSH),t.xp6(1),t.Q6J("matMenuTriggerFor",a),t.xp6(3),t.Oqu(t.lcZ(7,11,"Cities")),t.xp6(4),t.Q6J("ngForOf",i.cities),t.xp6(1),t.Q6J("routerLink",t.VKq(23,$,i.code)),t.xp6(1),t.Oqu(t.lcZ(13,13,"Contact")),t.xp6(3),t.Q6J("matMenuTriggerFor",T),t.xp6(3),t.Oqu(t.lcZ(19,15,"Language")),t.xp6(5),t.Oqu(t.lcZ(24,17,"Polish")),t.xp6(3),t.Oqu(t.lcZ(27,19,"English")),t.xp6(5),t.Oqu(t.lcZ(32,21,"Login"))}},dependencies:[m.sg,A.Ye,h.lW,d.VK,d.OP,d.p6,c.rH,p.X$],styles:[".spacer[_ngcontent-%COMP%]{flex:1 1 auto}.toolbar-logo[_ngcontent-%COMP%]{height:100%;max-height:64px;width:auto}"]})}return e})(),M=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-default"]],decls:2,vars:0,template:function(o,i){1&o&&(t.TgZ(0,"p"),t._uU(1,"default works!"),t.qZA())}})}return e})(),E=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-footer-antal"]],decls:3,vars:3,consts:[[1,"text-center"]],template:function(o,i){1&o&&(t.TgZ(0,"p",0),t._uU(1),t.ALo(2,"translate"),t.qZA()),2&o&&(t.xp6(1),t.hij(" \xa9 ",t.lcZ(2,1,"Copy Rights Antal"),"\n"))},dependencies:[p.X$],styles:["p[_ngcontent-%COMP%]{width:70%;margin:30px auto 0;padding-top:15px;border-top:1px solid #c9b9b9;font-size:1.1rem}"]})}return e})(),Q=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-footer-dode"]],decls:3,vars:3,consts:[[1,"text-center"]],template:function(o,i){1&o&&(t.TgZ(0,"p",0),t._uU(1),t.ALo(2,"translate"),t.qZA()),2&o&&(t.xp6(1),t.hij(" \xa9 ",t.lcZ(2,1,"Copy Rights DomDevelopment"),"\n"))},dependencies:[p.X$],styles:["p[_ngcontent-%COMP%]{width:70%;margin:30px auto 0;padding-top:15px;border-top:1px solid #c9b9b9;font-size:1.1rem}"]})}return e})();const R=["headerContainer"],X=["footerContainer"];function K(e,l){}function G(e,l){}let W=(()=>{class e{constructor(n){this.codeStatusService=n,this.title="app"}ngAfterViewInit(){this.codeStatusService.fetchStatusCode().subscribe(n=>{this.statusCode=n,console.log("code",this.statusCode.code),this.loadDynamicComponents(this.statusCode.code)})}ngOnInit(){}loadDynamicComponents(n){let o,i;switch(n){case"antal":o=k,i=E;break;case"domdevelopment":o=j,i=Q;break;default:o=M,i=M}this.headerContainer.clear(),this.footerContainer.clear(),this.headerContainer.createComponent(o),this.footerContainer.createComponent(i)}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(y))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-root"]],viewQuery:function(o,i){if(1&o&&(t.Gf(R,7,t.s_b),t.Gf(X,7,t.s_b)),2&o){let a;t.iGM(a=t.CRH())&&(i.headerContainer=a.first),t.iGM(a=t.CRH())&&(i.footerContainer=a.first)}},decls:6,vars:0,consts:[["headerContainer",""],["footerContainer",""]],template:function(o,i){1&o&&(t.YNc(0,K,0,0,"ng-template",null,0,t.W1O),t.TgZ(2,"main"),t._UZ(3,"router-outlet"),t.qZA(),t.YNc(4,G,0,0,"ng-template",null,1,t.W1O))},dependencies:[c.lC]})}return e})();var Z=s(617),x=s(9116),L=s(2032),r=s(5195);let V=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[A.g0,Z.Ps,h.ot,x.lN,L.c,d.Tx,r.QW]})}return e})();var v=s(6223),tt=s(349);let C=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[m.ez,m.ez,V,v.u5,v.UX,tt.PW]})}return e})(),et=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[C,c.Bz,p.aw]})}return e})();function nt(e,l){1&e&&(t.ynx(0),t._UZ(1,"img",1),t.BQk())}function ot(e,l){1&e&&(t.ynx(0),t._UZ(1,"img",2),t.BQk())}const it=[{path:"",component:(()=>{class e{constructor(n){this.codeService=n}getCode(){this.codeService.fetchStatusCode().subscribe(n=>{this.code=n.code})}ngOnInit(){this.getCode()}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(y))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-home"]],decls:2,vars:2,consts:[[4,"ngIf"],["src","assets/img/domdevelopment/domdevelopment_home_page.jpg","alt","Descriptive Alt Text",1,"responsive-image"],["src","assets/img/antal/antal_home_page.jpg","alt","Descriptive Alt Text",1,"responsive-image"]],template:function(o,i){1&o&&(t.YNc(0,nt,2,0,"ng-container",0),t.YNc(1,ot,2,0,"ng-container",0)),2&o&&(t.Q6J("ngIf","domdevelopment"===i.code),t.xp6(1),t.Q6J("ngIf","antal"===i.code))},dependencies:[m.O5],styles:[".responsive-image[_ngcontent-%COMP%]{max-width:100%;height:auto;display:block;margin:20px auto 0}"]})}return e})()}];let st=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[c.Bz.forChild(it),c.Bz]})}return e})(),at=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[C,st]})}return e})();const ct=[{path:"login",component:(()=>{class e{constructor(){this.hide=!0}onLogin(){}static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-login"]],decls:21,vars:4,consts:[[1,"login-wrapper","mt-3"],[3,"ngSubmit"],["f","ngForm"],["matInput",""],["matInput","",3,"type"],["mat-icon-button","","matSuffix","",3,"click"],[1,"d-flex","justify-content-end"],["mat-raised-button","","color","primary"]],template:function(o,i){1&o&&(t.TgZ(0,"div",0)(1,"h2"),t._uU(2,"Login"),t.qZA(),t.TgZ(3,"form",1,2),t.NdJ("ngSubmit",function(){return i.onLogin()}),t.TgZ(5,"div")(6,"mat-form-field")(7,"mat-label"),t._uU(8,"User Name"),t.qZA(),t._UZ(9,"input",3),t.qZA()(),t.TgZ(10,"div")(11,"mat-form-field")(12,"mat-label"),t._uU(13,"Password"),t.qZA(),t._UZ(14,"input",4),t.TgZ(15,"button",5),t.NdJ("click",function(){return i.hide=!i.hide}),t.TgZ(16,"mat-icon"),t._uU(17),t.qZA()()()(),t.TgZ(18,"div",6)(19,"button",7),t._uU(20,"Login"),t.qZA()()()()),2&o&&(t.xp6(14),t.Q6J("type",i.hide?"password":"text"),t.xp6(1),t.uIk("aria-label","Hide password")("aria-pressed",i.hide),t.xp6(2),t.Oqu(i.hide?"visibility_off":"visibility"))},dependencies:[Z.Hw,h.lW,h.RK,x.KE,x.hX,x.R9,L.Nt,v._Y,v.JL,v.F],styles:[".login-wrapper[_ngcontent-%COMP%]{max-width:600px;margin:0 auto}"]})}return e})()}];let rt=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[c.Bz.forChild(ct),c.Bz]})}return e})(),lt=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[C,rt]})}return e})();var mt=s(5061),pt=s(8645),w=s(9773),ut=s(940),dt=s(6306),ht=s(8504);let gt=(()=>{class e{constructor(n){this.http=n,this.apiUrl="https://localhost:8081/api/system/investments",this.cache$=null,this.cacheExpirationMs=1e5,this.lastRequestTime=null}getInvestments(){const n=(new Date).getTime();return this.cache$&&this.lastRequestTime&&n-this.lastRequestTime<this.cacheExpirationMs?(console.log("Fetching data from cache Investments"),this.cache$):(console.log("Fetching data from API Investments"),this.cache$=this.http.get(this.apiUrl).pipe((0,F.b)(()=>this.lastRequestTime=(new Date).getTime()),(0,ut.d)(1),(0,dt.K)(o=>(console.error("Error fetching investments data:",o),(0,ht._)(()=>o)))),this.cache$)}static#t=this.\u0275fac=function(o){return new(o||e)(t.LFG(u.eN))};static#e=this.\u0275prov=t.Yz7({token:e,factory:e.\u0275fac,providedIn:"root"})}return e})(),ft=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-investment"]],inputs:{investment:"investment"},decls:14,vars:3,consts:[[1,"investment-card"],["mat-card-avatar","",1,"investment-image"],["mat-card-image","","src","assets/img/domdevelopment/domdevelopment_home_page.jpg","alt","Osiedle Harmonia"],["mat-button",""]],template:function(o,i){1&o&&(t.TgZ(0,"mat-card",0)(1,"mat-card-header"),t._UZ(2,"div",1),t.TgZ(3,"mat-card-title"),t._uU(4),t.qZA(),t.TgZ(5,"mat-card-subtitle"),t._uU(6),t.qZA()(),t._UZ(7,"img",2),t.TgZ(8,"mat-card-content")(9,"p"),t._uU(10),t.qZA()(),t.TgZ(11,"mat-card-actions")(12,"button",3),t._uU(13,"VIEW DETAILS"),t.qZA()()()),2&o&&(t.xp6(4),t.Oqu(i.investment.name),t.xp6(2),t.Oqu(i.investment.addressStreet),t.xp6(4),t.Oqu(i.investment.description))},dependencies:[h.lW,r.a8,r.hq,r.kc,r.dn,r.dk,r.G2,r.$j,r.n5],styles:[".investment-card[_ngcontent-%COMP%]{max-width:400px;margin:20px auto}.investment-image[_ngcontent-%COMP%]{background-size:cover;width:40px;height:40px}"]})}return e})();function vt(e,l){if(1&e&&(t.ynx(0),t._UZ(1,"app-investment",4),t.BQk()),2&e){const n=l.$implicit;t.xp6(1),t.Q6J("investment",n)}}function Ct(e,l){if(1&e&&(t.TgZ(0,"div",2),t.YNc(1,vt,2,1,"ng-container",3),t.qZA()),2&e){const n=t.oxw();t.xp6(1),t.Q6J("ngForOf",n.investments)}}function _t(e,l){1&e&&(t.TgZ(0,"h5"),t._uU(1,"Aktualnie brak inwestycji"),t.qZA())}const yt=[{path:"city/:name",component:(()=>{class e{constructor(n,o,i){this.service=n,this.route=o,this.cityService=i,this.allInvestments=[],this.investments=[],this.cities=[],this.cityName="",this.unsubscribe$=new pt.x}getInvestments(){this.service.getInvestments().pipe((0,w.R)(this.unsubscribe$)).subscribe({next:n=>{this.allInvestments=n.investments,this.investments=n.investments,this.filterInvestmentsByCity(this.cityName)},error:n=>console.error("Error fetching investments:",n)})}getCities(){this.cityService.getCities().pipe((0,w.R)(this.unsubscribe$)).subscribe({next:n=>this.cities=n.cities,error:n=>console.error("Error fetching cities:",n)})}ngOnInit(){this.getCities(),this.getInvestments(),this.route.paramMap.subscribe(n=>{this.cityName=n.get("name")??"",this.allInvestments.length>0&&this.filterInvestmentsByCity(this.cityName)})}filterInvestmentsByCity(n){if(!n)return this.investments=[...this.allInvestments],void console.log("No city selected, copy all investments");const o=this.cities.find(i=>i.name===n);o?(this.investments=this.allInvestments.filter(i=>i.cityId===o.id),console.log(`Found ${this.investments.length} investments for city ${n}`)):(this.investments=[...this.allInvestments],console.log("No matching city found, displaying all investments"))}ngOnDestroy(){this.unsubscribe$.next(),this.unsubscribe$.complete()}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(gt),t.Y36(c.gz),t.Y36(b))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-investment-list"]],decls:2,vars:2,consts:[["class","card-container",4,"ngIf"],[4,"ngIf"],[1,"card-container"],[4,"ngFor","ngForOf"],[3,"investment"]],template:function(o,i){1&o&&(t.YNc(0,Ct,2,1,"div",0),t.YNc(1,_t,2,0,"h5",1)),2&o&&(t.Q6J("ngIf",i.investments.length>0),t.xp6(1),t.Q6J("ngIf",0===i.investments.length))},dependencies:[m.sg,m.O5,ft],styles:[".card-container[_ngcontent-%COMP%]{justify-content:center;display:grid;grid-template-columns:repeat(auto-fill,minmax(250px,1fr));gap:10px;max-width:1200px;margin:0 auto}@media (max-width: 768px){.card-container[_ngcontent-%COMP%]{grid-template-columns:1fr}}"]})}return e})()}];let Zt=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[c.Bz.forChild(yt),c.Bz]})}return e})(),xt=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[m.ez,Zt,C]})}return e})(),I=(()=>{class e{constructor(n){this.http=n,this.apiUrl="https://localhost:8081/api/system/developers/code"}fetchDevelopers(){return this.http.get(`${this.apiUrl}`)}static#t=this.\u0275fac=function(o){return new(o||e)(t.LFG(u.eN))};static#e=this.\u0275prov=t.Yz7({token:e,factory:e.\u0275fac,providedIn:"root"})}return e})();function bt(e,l){if(1&e&&(t.TgZ(0,"mat-card",1),t._UZ(1,"mat-card-header"),t.TgZ(2,"mat-card-content")(3,"div",2)(4,"mat-icon",3),t._uU(5,"place"),t.qZA(),t.TgZ(6,"div")(7,"div"),t._uU(8),t.qZA(),t.TgZ(9,"div"),t._uU(10),t.qZA()()(),t.TgZ(11,"div",2)(12,"mat-icon",4),t._uU(13,"phone"),t.qZA(),t.TgZ(14,"div"),t._uU(15),t.qZA()(),t.TgZ(16,"div",2)(17,"mat-icon",5),t._uU(18,"fax"),t.qZA(),t.TgZ(19,"div"),t._uU(20),t.qZA()(),t.TgZ(21,"div",2)(22,"mat-icon",6),t._uU(23,"email"),t.qZA(),t.TgZ(24,"div"),t._uU(25),t.qZA()()()()),2&e){const n=l.$implicit;t.xp6(8),t.Oqu(n.name),t.xp6(2),t.xDo("",n.addressStreet," ",n.addressBuildingNumber,"/",n.addressFlatNumber,", ",n.addressPostalCode,", ",n.addressCountry,""),t.xp6(5),t.Oqu(n.telephoneNumber),t.xp6(5),t.Oqu(n.faxNumber),t.xp6(5),t.Oqu(n.email)}}function Tt(e,l){if(1&e&&(t.TgZ(0,"mat-card",1),t._UZ(1,"mat-card-header"),t.TgZ(2,"mat-card-content")(3,"div",2)(4,"mat-icon",3),t._uU(5,"place"),t.qZA(),t.TgZ(6,"div")(7,"div"),t._uU(8),t.qZA(),t.TgZ(9,"div"),t._uU(10),t.qZA()()(),t.TgZ(11,"div",2)(12,"mat-icon",4),t._uU(13,"phone"),t.qZA(),t.TgZ(14,"div"),t._uU(15),t.qZA()(),t.TgZ(16,"div",2)(17,"mat-icon",5),t._uU(18,"fax"),t.qZA(),t.TgZ(19,"div"),t._uU(20),t.qZA()(),t.TgZ(21,"div",2)(22,"mat-icon",6),t._uU(23,"email"),t.qZA(),t.TgZ(24,"div"),t._uU(25),t.qZA()()()()),2&e){const n=l.$implicit;t.xp6(8),t.Oqu(n.name),t.xp6(2),t.xDo("",n.addressStreet," ",n.addressBuildingNumber,"/",n.addressFlatNumber,", ",n.addressPostalCode,", ",n.addressCountry,""),t.xp6(5),t.Oqu(n.telephoneNumber),t.xp6(5),t.Oqu(n.faxNumber),t.xp6(5),t.Oqu(n.email)}}const Ut=[{path:"contact/domdevelopment",component:(()=>{class e{constructor(n){this.service=n}ngOnInit(){this.getDevelopers()}getDevelopers(){this.service.fetchDevelopers().subscribe(n=>{this.developers=n})}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(I))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-contact-dode"]],decls:1,vars:1,consts:[["class","contact-card",4,"ngFor","ngForOf"],[1,"contact-card"],[1,"contact-item"],["aria-hidden","false","aria-label","Location"],["aria-hidden","false","aria-label","Phone"],["aria-hidden","false","aria-label","Fax"],["aria-hidden","false","aria-label","Email"]],template:function(o,i){1&o&&t.YNc(0,bt,26,9,"mat-card",0),2&o&&t.Q6J("ngForOf",i.developers.developers)},dependencies:[m.sg,Z.Hw,r.a8,r.dn,r.dk],styles:[".contact-card[_ngcontent-%COMP%]{max-width:600px;margin:2em auto;border-radius:16px;box-shadow:0 8px 30px #0000001f;overflow:hidden;font-family:Roboto,sans-serif;background:#fff;transition:box-shadow .3s ease-in-out}.contact-card[_ngcontent-%COMP%]:hover{box-shadow:0 15px 40px #00000029}.mat-card-header[_ngcontent-%COMP%]{background-color:transparent;color:#333;padding:24px;border-bottom:1px solid #eeeeee}.mat-card-title[_ngcontent-%COMP%]{font-size:1.5rem;font-weight:300;color:#555;margin-bottom:.5em}.mat-card-content[_ngcontent-%COMP%]{padding:24px;color:#333;font-size:1rem;line-height:1.5}.contact-item[_ngcontent-%COMP%]{display:flex;align-items:center;margin-bottom:16px}.contact-item[_ngcontent-%COMP%]   mat-icon[_ngcontent-%COMP%]{margin-right:16px;color:#3f51b5;font-size:1.25rem}.contact-item[_ngcontent-%COMP%]   span[_ngcontent-%COMP%]{display:block}.contact-item-icon[_ngcontent-%COMP%]{width:24px;height:24px;margin-right:8px}a[_ngcontent-%COMP%]{text-decoration:none;color:#3f51b5;transition:color .3s ease}a[_ngcontent-%COMP%]:hover{color:#ff4081}@media (max-width: 768px){.contact-card[_ngcontent-%COMP%]{margin:1em;border-radius:8px}}"]})}return e})()},{path:"contact/antal",component:(()=>{class e{constructor(n){this.service=n}ngOnInit(){this.getDevelopers()}getDevelopers(){this.service.fetchDevelopers().subscribe(n=>{this.developers=n})}static#t=this.\u0275fac=function(o){return new(o||e)(t.Y36(I))};static#e=this.\u0275cmp=t.Xpm({type:e,selectors:[["app-contact-antal"]],decls:1,vars:1,consts:[["class","contact-card",4,"ngFor","ngForOf"],[1,"contact-card"],[1,"contact-item"],["aria-hidden","false","aria-label","Location"],["aria-hidden","false","aria-label","Phone"],["aria-hidden","false","aria-label","Fax"],["aria-hidden","false","aria-label","Email"]],template:function(o,i){1&o&&t.YNc(0,Tt,26,9,"mat-card",0),2&o&&t.Q6J("ngForOf",i.developers.developers)},dependencies:[m.sg,Z.Hw,r.a8,r.dn,r.dk],styles:[".contact-card[_ngcontent-%COMP%]{width:100%;max-width:600px;margin:0 auto}.contact-card[_ngcontent-%COMP%]   .mat-card-title[_ngcontent-%COMP%]{text-align:center;margin-bottom:20px}.contact-card[_ngcontent-%COMP%]   .contact-item[_ngcontent-%COMP%]{display:flex;align-items:center;margin-bottom:10px}.contact-card[_ngcontent-%COMP%]   .contact-item[_ngcontent-%COMP%]   mat-icon[_ngcontent-%COMP%]{margin-right:8px}.contact-card[_ngcontent-%COMP%]   .contact-item[_ngcontent-%COMP%]   span[_ngcontent-%COMP%]{margin-right:4px}.contact-card[_ngcontent-%COMP%]   .contact-item[_ngcontent-%COMP%]   span[_ngcontent-%COMP%]:last-child{margin-right:0}"]})}return e})()}];let Ot=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[c.Bz.forChild(Ut),c.Bz]})}return e})(),Ft=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e});static#n=this.\u0275inj=t.cJS({imports:[m.ez,C,Ot]})}return e})(),Mt=(()=>{class e{static#t=this.\u0275fac=function(o){return new(o||e)};static#e=this.\u0275mod=t.oAB({type:e,bootstrap:[W]});static#n=this.\u0275inj=t.cJS({imports:[_.b2,at,Ft,lt,et,xt,q,u.JF,p.aw.forRoot({loader:{provide:p.Zw,useFactory:Lt,deps:[u.eN]}})]})}return e})();function Lt(e){return new mt.w(e)}_.q6().bootstrapModule(Mt).catch(e=>console.error(e))}},g=>{g.O(0,[736],()=>g(g.s=390)),g.O()}]);