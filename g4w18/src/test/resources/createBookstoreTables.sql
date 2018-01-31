/**
 * Authors:  Jephtia, Sebastian
 * Created: Jan 25, 2018
 */

USE BOOKSTORE;

DROP TABLE IF EXISTS BOOK_AUTHORS;
DROP TABLE IF EXISTS INVOICE_DETAILS;
DROP TABLE IF EXISTS MASTER_INVOICES;
DROP TABLE IF EXISTS REVIEWS;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS AUTHORS;
DROP TABLE IF EXISTS TAXES;

CREATE TABLE BOOKS (
    BOOK_ID int NOT NULL AUTO_INCREMENT,
    ISBN_NUMBER varchar(14) NOT NULL default '',
    TITLE varchar(100) NOT NULL default '',
    PUBLISHER varchar(50) NOT NULL default '',
    PUBLICATION_DATE timestamp default CURRENT_TIMESTAMP,
    PAGE_NUMBER int(5) NOT NULL default 1,
    GENRE varchar(20) NOT NULL default '',
    DESCRIPTION MEDIUMTEXT NOT NULL,
    FORMAT varchar(10) NOT NULL default '',
    WHOLESALE_PRICE decimal(5,2) NOT NULL default 0.00,
    LIST_PRICE decimal(5,2) NOT NULL default 0.00,
    SALE_PRICE decimal(5,2) NOT NULL default 0.00,
    INVENTORY_DATE timestamp default CURRENT_TIMESTAMP,
    PRIMARY KEY (BOOK_ID)
) ENGINE=InnoDB;

CREATE TABLE AUTHORS (
    AUTHOR_ID int NOT NULL auto_increment,
    FIRST_NAME varchar(50) NOT NULL default '',
    LAST_NAME varchar(50) NOT NULL default '',
    PRIMARY KEY (AUTHOR_ID)
) ENGINE=InnoDB;

CREATE TABLE BOOK_AUTHORS (
    BOOK_AUTHOR_ID int NOT NULL AUTO_INCREMENT,
    BOOK_ID int NOT NULL,
    AUTHOR_ID int NOT NULL,
    PRIMARY KEY (BOOK_AUTHOR_ID),
    FOREIGN KEY (BOOK_ID) REFERENCES BOOKS(BOOK_ID),
    FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHORS(AUTHOR_ID)
) ENGINE=InnoDB;

CREATE TABLE USERS (
    USER_ID int NOT NULL AUTO_INCREMENT,
    USERNAME varchar(50) NOT NULL UNIQUE default '',
    PASSWORD varchar(50) NOT NULL default '',
    TITLE varchar(50) NOT NULL default '',
    FIRST_NAME varchar(50) NOT NULL default '',
    LAST_NAME varchar(50) NOT NULL default '',
    COMPANY_NAME varchar(50) NOT NULL default '',
    ADDRESS_1 varchar(100) NOT NULL default '',
    ADDRESS_2 varchar(100) NOT NULL default '',
    CITY varchar(20) NOT NULL default '',
    PROVINCE varchar(2) NOT NULL default '',
    COUNTRY varchar(20) NOT NULL default '',
    POSTAL_CODE varchar(6) NOT NULL default '',
    HOME_TELEPHONE varchar(12) NOT NULL default '',
    CELLPHONE varchar(12) NOT NULL default '',
    EMAIL varchar(50) NOT NULL UNIQUE default '',
    IS_MANAGER boolean NOT NULL default FALSE,
    PRIMARY KEY (USER_ID)
) ENGINE=InnoDB;

CREATE TABLE REVIEWS (
    REVIEW_ID int NOT NULL AUTO_INCREMENT,
    BOOK_ID int NOT NULL,
    USER_ID int NOT NULL,
    REVIEW_DATE timestamp NOT NULL default CURRENT_TIMESTAMP,
    RATING int(1) NOT NULL default 1,
    REVIEW MEDIUMTEXT NOT NULL,
    APPROVAL_STATUS boolean NOT NULL default FALSE,
    PRIMARY KEY (REVIEW_ID),
    FOREIGN KEY (BOOK_ID) REFERENCES BOOKS(BOOK_ID),
    FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID)
) ENGINE=InnoDB;

CREATE TABLE MASTER_INVOICES (
    INVOICE_ID int NOT NULL AUTO_INCREMENT,
    USER_ID int NOT NULL,
    SALE_DATE timestamp NOT NULL default CURRENT_TIMESTAMP,
    NET_VALUE decimal(6,2) NOT NULL default 0.00,
    GROSS_VALUE decimal(6,2) NOT NULL default 0.00,
    PRIMARY KEY (INVOICE_ID),
    FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID)
) ENGINE=InnoDB;

CREATE TABLE INVOICE_DETAILS (
    DETAIL_ID int NOT NULL AUTO_INCREMENT,
    INVOICE_ID int NOT NULL,
    BOOK_ID int NOT NULL,
    BOOK_PRICE decimal(6,2) NOT NULL default 0.00,
    GST_RATE decimal (6,2) NOT NULL default 0.00,
    PST_RATE decimal (6,2) NOT NULL default 0.00,
    HST_RATE decimal (6,2) NOT NULL default 0.00,
    PRIMARY KEY (DETAIL_ID),
    FOREIGN KEY (INVOICE_ID) REFERENCES MASTER_INVOICES(INVOICE_ID),
    FOREIGN KEY (BOOK_ID) REFERENCES BOOKS(BOOK_ID)
) ENGINE=InnoDB;

CREATE TABLE TAXES (
    TAX_ID int NOT NULL AUTO_INCREMENT,
    PROVINCE varchar(2) NOT NULL default '',
    GST_RATE decimal (6,3) NOT NULL default 0.00,
    PST_RATE decimal (6,3) NOT NULL default 0.00,
    HST_RATE decimal (6,3) NOT NULL default 0.00,
    PRIMARY KEY (TAX_ID)
) ENGINE=InnoDB;

INSERT INTO BOOKS values
(null, "978-1408855652", "Harry Potter and the Philosopher's Stone", "Bloomsbury Children's Books", "2014-09-01 00:00:00", 352, "Fantasy", 
"Harry Potter has never even heard of Hogwarts when the letters start dropping on the doormat at number four, Privet Drive. Addressed in green ink on yellowish parchment with a purple seal, they are swiftly confiscated by his grisly aunt and uncle. Then, on Harry's eleventh birthday, a great beetle-eyed giant of a man called Rubeus Hagrid bursts in with some astonishing news: Harry Potter is a wizard, and he has a place at Hogwarts School of Witchcraft and Wizardry. An incredible adventure is about to begin! These new editions of the classic and internationally bestselling, multi-award-winning series feature instantly pick-up-able new jackets by Jonny Duddle, with huge child appeal, to bring Harry Potter to the next generation of readers. It's time to PASS THE MAGIC ON ...",
"PDF", 6.99, 10.99, 8.99, null),
(null, "978-0553573404", "A Game of Thrones: A Song of Ice and Fire: Book One", "Bantam", "1997-08-04 00:00:00", 864, "Fantasy",
"Long ago, in a time forgotten, a preternatural event threw the seasons out of balance. In a land where summers can last decades and winters a lifetime, trouble is brewing. The cold is returning, and in the frozen wastes to the north of Winterfell, sinister forces are massing beyond the kingdom�s protective Wall. To the south, the king�s powers are failing�his most trusted adviser dead under mysterious circumstances and his enemies emerging from the shadows of the throne. At the center of the conflict lie the Starks of Winterfell, a family as harsh and unyielding as the frozen land they were born to. Now Lord Eddard Stark is reluctantly summoned to serve as the king�s new Hand, an appointment that threatens to sunder not only his family but the kingdom itself.

Sweeping from a harsh land of cold to a summertime kingdom of epicurean plenty, A Game of Thrones tells a tale of lords and ladies, soldiers and sorcerers, assassins and bastards, who come together in a time of grim omens. Here an enigmatic band of warriors bear swords of no human metal; a tribe of fierce wildlings carry men off into madness; a cruel young dragon prince barters his sister to win back his throne; a child is lost in the twilight between life and death; and a determined woman undertakes a treacherous journey to protect all she holds dear. Amid plots and counter-plots, tragedy and betrayal, victory and terror, allies and enemies, the fate of the Starks hangs perilously in the balance, as each side endeavors to win that deadliest of conflicts: the game of thrones.

Unparalleled in scope and execution, A Game of Thrones is one of those rare reading experiences that catch you up from the opening pages, won�t let you go until the end, and leave you yearning for more.",
"PDF", 4.99, 6.99, 5.99, null),
(null, "978-0316029186", "The Last Wish", "Orbit", "2008-05-01 00:00:00", 384, "Fantasy",
"Geralt of Rivia is a witcher. A cunning sorcerer. A merciless assassin.And a cold-blooded killer.His sole purpose: to destroy the monsters that plague the world.But not everything monstrous-looking is evil and not everything fair is good. . . and in every fairy tale there is a grain of truth.",
"PDF", 3.99, 8.99, 5.99, null),
(null, "978-0261102217", "The Hobbit", "HarperCollins", "2011-10-27 00:00:00", 400, "Fantasy",
"Bilbo Baggins is a hobbit who enjoys a comfortable, unambitious life, rarely travelling further than the pantry of his hobbit-hole in Bag End. But his contentment is disturbed when the wizard, Gandalf, and a company of thirteen dwarves arrive on his doorstep one day to whisk him away on an unexpected journey �there and back again�. They have a plot to raid the treasure hoard of Smaug the Magnificent, a large and very dangerous dragon",
"PDF", 5.99, 11.99, 7.99, null),
(null, "978-0007322596", "The Lord of the Rings: The Fellowship of the Ring, The Two Towers, The Return of the King", "HarperCollins", "2009-04-20 00:00:00", 1209, "Fantasy",
"All three parts of the epic masterpiece The Lord of the Rings � The Fellowship of the Ring, The Two Towers & The Return of the King � available as one download, featuring the definitive edition of the text, hyperlinked footnotes and page references, and 3 maps including a detailed map of Middle-earth.Sauron, the Dark Lord, has gathered to him all the Rings of Power � the means by which he intends to rule Middle-earth. All he lacks in his plans for dominion is the One Ring � the ring that rules them all � which has fallen into the hands of the hobbit, Bilbo Baggins.In a sleepy village in the Shire, young Frodo Baggins finds himself faced with an immense task, as the Ring is entrusted to his care. He must leave his home and make a perilous journey across the realms of Middle-earth to the Crack of Doom, deep inside the territories of the Dark Lord. There he must destroy the Ring forever and foil the Dark Lord in his evil purpose.",
"PDF", 12.99, 18.99, 15.99, null),
(null, "978-0261102736", "The Silmarillion", "HarperCollins", "2011-02-03 00:00:00", 357, "Fantasy",
"The Silmarillion is an account of the Elder Days, of the First Age of Tolkien�s world. It is the ancient drama to which the characters in The Lord of the Rings look back, and in whose events some of them such as Elrond and Galadriel took part. The tales of The Silmarillion are set in an age when Morgoth, the first Dark Lord, dwelt in Middle-Earth, and the High Elves made war upon him for the recovery of the Silmarils, the jewels containing the pure light of Valinor.Included in the book are several shorter works. The Ainulindale is a myth of the Creation and in the Valaquenta the nature and powers of each of the gods is described. The Akallabeth recounts the downfall of the great island kingdom of N�menor at the end of the Second Age and Of the Rings of Power tells of the great events at the end of the Third Age, as narrated in The Lord of the Rings.",
"PDF", 5.99, 9.99, 6.99, null),
(null, "978-0060598242", "The Chronicles of Narnia", "HarperCollins", "2004-10-26 00:00:00", 784, "Fantasy",
"Fantastic creatures, heroic deeds, epic battles in the war between good and evil, and unforgettable adventures come together in this world where magic meets reality, which has been enchanting readers of all ages for over sixty years. The Chronicles of Narnia has transcended the fantasy genre to become a part of the canon of classic literature.",
"PDF", 9.99, 15.99, 12.99, null),
(null, "978-2895490845", "Amos Daragon, The Mask Wearer", "Bluefire", "2012-03-13 00:00:00", 176, "Fantasy",
"Amos Daragon's life changes forever the day a mermaid gives him a mask capable of harnessing the strength of the wind�and appoints Amos as the new Mask Wearer. His task: to find the masks for the other elements, earth, fire, and water. Only then will Amos be fully empowered to battle the evil forces that threaten to destroy the balance of nature and plunge the world into darkness.To fulfill his destiny, Amos must make his way to the mysterious woods of Tarkasis. But a wicked sorcerer is terrorizing the land, searching for a skull pendant that was stolen from him�a pendant that conceals a secret weapon. What will Amos do when the pendant falls into his hands? Will Beorf, a boy who can morph into a bear, and Medusa, a snake-haired gorgon, turn out to be friend or foe? And will Amos master any of his newfound skills as Mask Wearer in time to face a formidable enemy? His chanllenges are great . . .  and they're just beginning.",
"PDF", 3.99, 7.99, 5.99, null),
(null, "978-1503951198", "The Jekyll Revelation", "47 North", "2016-11-08 00:00:00", 493, "Fantasy",
"A chilling curse is transported from 1880s London to present-day California, awakening a long-dormant fiend.
While on routine patrol in the tinder-dry Topanga Canyon, environmental scientist Rafael Salazar expects to find animal poachers, not a dilapidated antique steamer trunk. Inside the peculiar case, he discovers a journal, written by the renowned Robert Louis Stevenson, which divulges ominous particulars about his creation of The Strange Case of Dr. Jekyll and Mr. Hyde. It also promises to reveal a terrible secret�the identity of Jack the Ripper.
Unfortunately, the journal�whose macabre tale unfolds in an alternating narrative with Rafe�s�isn�t the only relic in the trunk, and Rafe isn�t the only one to purloin a souvenir. A mysterious flask containing the last drops of the grisly potion that inspired Jekyll and Hyde and spawned London�s most infamous killer has gone missing. And it has definitely fallen into the wrong hands.",
"PDF", 4.99, 14.95, 9.5, null),
(null, "978-1250122957", "Year One: Chronicles of The One", "St. Martin's Press", "2017-12-05 00:00:00", 419, "Fantasy",
"It began on New Year�s Eve.

The sickness came on suddenly, and spread quickly. The fear spread even faster. Within weeks, everything people counted on began to fail them. The electrical grid sputtered; law and government collapsed�and more than half of the world�s population was decimated.

Where there had been order, there was now chaos. And as the power of science and technology receded, magick rose up in its place. Some of it is good, like the witchcraft worked by Lana Bingham, practicing in the loft apartment she shares with her lover, Max. Some of it is unimaginably evil, and it can lurk anywhere, around a corner, in fetid tunnels beneath the river�or in the ones you know and love the most.

As word spreads that neither the immune nor the gifted are safe from the authorities who patrol the ravaged streets, and with nothing left to count on but each other, Lana and Max make their way out of a wrecked New York City. At the same time, other travelers are heading west too, into a new frontier. Chuck, a tech genius trying to hack his way through a world gone offline. Arlys, a journalist who has lost her audience but uses pen and paper to record the truth. Fred, her young colleague, possessed of burgeoning abilities and an optimism that seems out of place in this bleak landscape. And Rachel and Jonah, a resourceful doctor and a paramedic who fend off despair with their determination to keep a young mother and three infants in their care alive.

In a world of survivors where every stranger encountered could be either a savage or a savior, none of them knows exactly where they are heading, or why. But a purpose awaits them that will shape their lives and the lives of all those who remain.

The end has come. The beginning comes next.",
"PDF", 15.99, 36, 15.99, null),
(null, "978-0316399746", "Valor", "Orbit", "2014-07-22 00:00:00", 688, "Fantasy", 
"Corban flees his homeland searching for peace, but he soon discovers that there is no haven in the west as the agents of Rhin and roaming bands of giants hound his every step.

Veradis leaves the battleground and rushes to his King's side. But he has witnessed both combat and betrayal and his duty weighs heavily upon him.

Maquin seeks only revenge, but pirate slavers and the brutal world of pit-fighting stand in his way.

Nathair becomes embroiled in the wars of the west as Queen Rhin marches against King Owain. The need to find the cauldron of the giants drives him on.",
"PDF", 7.99, 12.99, 9.99, null),
(null, "978-1250169495", "Oathbringer: Book Three of the Stormlight Archive", "Tor Books", "2017-11-14 00:00:00", 1220, "Fantasy", 
"In Oathbringer, the third volume of the New York Times bestselling Stormlight Archive, humanity faces a new Desolation with the return of the Voidbringers, a foe with numbers as great as their thirst for vengeance.

Dalinar Kholin�s Alethi armies won a fleeting victory at a terrible cost: The enemy Parshendi summoned the violent Everstorm, which now sweeps the world with destruction, and in its passing awakens the once peaceful and subservient parshmen to the horror of their millennia-long enslavement by humans. While on a desperate flight to warn his family of the threat, Kaladin Stormblessed must come to grips with the fact that the newly kindled anger of the parshmen may be wholly justified.

Nestled in the mountains high above the storms, in the tower city of Urithiru, Shallan Davar investigates the wonders of the ancient stronghold of the Knights Radiant and unearths dark secrets lurking in its depths. And Dalinar realizes that his holy mission to unite his homeland of Alethkar was too narrow in scope. Unless all the nations of Roshar can put aside Dalinar�s blood-soaked past and stand together�and unless Dalinar himself can confront that past�even the restoration of the Knights Radiant will not prevent the end of civilization.",
"PDF", 17.91, 22.41, 11.91, null),
(null, "978-0316389709", "Swords of Destiny", "Orbit", "2015-12-05 00:00:00", 400, "Fantasy",
"Geralt is a witcher, a man whose magic powers, enhanced by long training and a mysterious elixir, have made him a brilliant fighter and a merciless assassin. Yet he is no ordinary murderer: his targets are the multifarious monsters and vile fiends that ravage the land and attack the innocent. 

This is a collection of short stories, following the adventures of the hit collection THE LAST WISH. Join Geralt as he battles monsters, demons and prejudices alike...",
"PDF", 12.99, 20.99, 15.36, null),
(null, "978-0770428778", "Dragonfly in Amber", "Seal Books", "2001-10-09 00:00:00", 976, "Fantasy", 
"From the author of Outlander... a magnificent epic that once again sweeps us back in time to the drama and passion of 18th-century Scotland...

For twenty years Claire Randall has kept her secrets. But now she is returning with her grown daughter to Scotland's majestic mist-shrouded hills. Here Claire plans to reveal a truth as stunning as the events that gave it birth: about the mystery of an ancient circle of standing stones ...about a love that transcends the boundaries of time ...and about James Fraser, a Scottish warrior whose gallantry once drew a young Claire from the security of her century to the dangers of his ....

Now a legacy of blood and desire will test her beautiful copper-haired daughter, Brianna, as Claire's spellbinding journey of self-discovery continues in the intrigue-ridden Paris court of Charles Stuart ...in a race to thwart a doomed Highlands uprising ...and in a desperate fight to save both the child and the man she loves....",
"PDF", 10.99, 20.66, 11.69, null),
(null, "978-0330492041", "The Hitchhiker's Guide To The Galaxy: The Trilogy Of Four", "PAN Macmillan Adult", "2002-03-08 00:00:00", 720, "Fantasy", 
"THE HITCHIKER'S GUIDE TO THE GALAXY: One Thursday lunchtime the Earth gets demolished to make way for a hyperspace bypass. For Arthur, who has just had his house demolished, this is too much. Sadly, the weekend's just begun. THE RESTAURANT AT THE END OF THE UNIVERSE: When all issues of space, time, matter and the nature of being are resolved, only one question remains: Where shall we have dinner? The Restaurant at the End of the Universe provides the ultimate gastronomic experience and, for once, there is no morning after. LIFE, THE UNIVERSE AND EVERYTHING: In consequence of a number of stunning catastrophes, Arthur Dent is surprised to find himself living in a hideously miserable cave on prehistoric Earth. And then, just as he thinks that things cannot possibly get any worse, they suddenly do. SO LONG, AND THANKS FOR ALL THE FISH: Arthur Dent's sense of reality is in its dickiest state when he suddenly finds the girl of his dreams. They go in search of God's Final Message and, in a dramatic break with tradition, actually find it.",
"PDF", 5.99, 48.81, 20.99, null),
(null, "978-0765376671", "The Way of Kings (The Stormlight Archive, Book 1)", "Tor Books", "2014-03-04 00:00:00", 1008, "Fantasy", 
"Roshar is a world of stone and storms. Uncanny tempests of incredible power sweep across the rocky terrain so frequently that they have shaped ecology and civilization alike. Animals hide in shells, trees pull in branches, and grass retracts into the soilless ground. Cities are built only where the topography offers shelter.

It has been centuries since the fall of the ten consecrated orders known as the Knights Radiant, but their Shardblades and Shardplate remain: mystical swords and suits of armor that transform ordinary men into near-invincible warriors. Men trade kingdoms for Shardblades. Wars were fought for them, and won by them.

One such war rages on a ruined landscape called the Shattered Plains. There, Kaladin, who traded his medical apprenticeship for a spear to protect his little brother, has been reduced to slavery. In a war that makes no sense, where ten armies fight separately against a single foe, he struggles to save his men and to fathom the leaders who consider them expendable.

Brightlord Dalinar Kholin commands one of those other armies. Like his brother, the late king, he is fascinated by an ancient text called The Way of Kings. Troubled by over-powering visions of ancient times and the Knights Radiant, he has begun to doubt his own sanity.

Across the ocean, an untried young woman named Shallan seeks to train under an eminent scholar and notorious heretic, Dalinar's niece, Jasnah. Though she genuinely loves learning, Shallan's motives are less than pure. As she plans a daring theft, her research for Jasnah hints at secrets of the Knights Radiant and the true cause of the war.

The result of over ten years of planning, writing, and world-building, The Way of Kings is but the opening movement of the Stormlight Archive, a bold masterpiece in the making.",
"PDF", 10.99, 26.99, 16.03, null),
(null, "978-0316437226", "Spice and Wolf Anniversary Collector's Edition", "Yen On", "2017-02-27 00:00:00", 944, "Fantasy", 
"Spice and Wolf is ten years old! To commemorate this anniversary, Yen Press is producing a lavish tome collecting all seventeen original volumes of Spice and Wolf. Beautifully bound and impressively designed to give the impression of a medieval manuscript, the book will also include all of illustrator Jyuu Ayakura's original illustrations. This book will be the crown jewel in any collector's library! The light novel series' story centers around the travels of the merchant Kraft Lawrence, who strikes a deal with the wolf god Holo that sees him transporting the homesick goddess back to her remote northern homeland. Uniquely exploring medieval trade, commerce, and religion, Lawrence plies his trade with Holo (in both human and wolf form) often becoming a decisive factor in his endeavors, but in the end, it is the story of these two memorable characters and their relationship to one another.",
"PDF", 219.99, 399.99, 249.99, null),
(null, "978-0316229296", "The Fifth Season", "Orbit", "2015-07-04 00:00:00", 512, "Fantasy", 
"A season of endings has begun. 

It starts with the great red rift across the heart of the world's sole continent, spewing ash that blots out the sun. 

It starts with death, with a murdered son and a missing daughter. 

It starts with betrayal, and long dormant wounds rising up to fester. 

This is the Stillness, a land long familiar with catastrophe, where the power of the earth is wielded as a weapon. And where there is no mercy. ",
"PDF", 12.99, 20.99, 18.89, null),
(null, "978-0765388247", "Shroud of Eternity: Sister of Darkness: The Nicci Chronicles, Volume II", "Tor Books", "2018-01-09 00:00:00", 528, "Fantasy",
"The formidable sorceress Nicci and her companions?the newly powerless Nathan and the youthful Bannon?set out on another quest after driving ruthless Norukai slavers out of Renda Bay. Their mission: restore Nathan�s magic and, for Nicci, save the world.

Guided by the witch-woman Red's mysterious prophecy, the trio makes their way south of Kol Adair towards a wondrous city shrouded behind time, Ildakar. But the grotesque omens on their path to Nathan's salvation?severed Norukai heads on pikes, a genetically modified monster, and a petrified army of half a million?are just a taste of the unimaginable horrors that await within the Shroud of Eternity.",
"PDF", 15.99, 30.69, 34.99, null),
(null, "978-0786965984", "Curse of Strahd: A Dungeons & Dragons Sourcebook", "Wizards of The Coast", "2016-03-15 00:00:00", 256, "Fantasy",
"Under raging storm clouds, the vampire Count Strahd von Zarovich stands silhouetted against the ancient walls of Castle Ravenloft. Rumbling thunder pounds the castle spires. The wind�s howling increases as he turns his gaze down toward the village of Barovia. Far below, yet not beyond his keen eyesight, a party of adventurers has just entered his domain. Strahd�s face forms the barest hint of a smile as his dark plan unfolds. He knew they were coming, and he knows why they came � all according to his plan. A lightning flash rips through the darkness, but Strahd is gone. Only the howling of the wind fills the midnight air. The master of Castle Ravenloft is having guests for dinner. And you are invited.",
"PDF", 43.7, 63.95, 51.71, null),
(null, "978-0241952894", "A Study in Scarlet", "Viking", "2011-09-27 00:00:00", 144, "Mystery", 
"There's a scarlet thread of murder running through the colourless skein of life, and our duty is to unravel it, and isolate it, and expose every inch of it.' From the moment Dr John Watson takes lodgings in Baker Street with the consulting detective Sherlock Holmes, he becomes intimately acquainted with the bloody violence and frightening ingenuity of the criminal mind. In A Study in Scarlet , Holmes and Watson's first mystery, the pair are summoned to a south London house where they find a dead man whose contorted face is a twisted mask of horror. The body is unmarked by violence but on the wall a mysterious word has been written in blood. The police are baffled by the crime and its circumstances. But when Sherlock Holmes applies his brilliantly logical mind to the problem he uncovers a tragic tale of love and deadly revenge . . .",
"PDF", 5.99, 10.99, 6.99, null),
(null, "978-0425169209", "The ABC Murders: A Hercule Poirot Mystery", "William Morrow Paperbacks", "2003-12-15 00:00:00", 240, "Mystery",
"There's a serial killer on the loose, bent on working his way though the alphabet. There seems little chance of the murderer being caught -- until her makes the crucial and vain mistake of challenging Hercule Poirot to frustrate his plans ...",
"PDF", 5.99, 11.99, 7.99, null),
(null, "978-0425173732", "Death on the Nile: Hercule Poirot investigates", "William Morrow Paperbacks", "2005-07-05 00:00:00", 320, "Mystery", 
"When a murder occurs aboard a Nile steamer, the passengers find themselves in a state of panic and emotional conflict.",
"PDF", 5.99, 11.99, 7.99, null),
(null, "978-1501163401", "Sleeping Beauties", "Scribner", "2017-09-26 00:00:00", 720, "Mystery", 
"In a future so real and near it might be now, something happens when women go to sleep: they become shrouded in a cocoon-like gauze. If they are awakened, if the gauze wrapping their bodies is disturbed or violated, the women become feral and spectacularly violent. And while they sleep they go to another place, a better place, where harmony prevails and conflict is rare.One woman, the mysterious �Eve Black,� is immune to the blessing or curse of the sleeping disease. Is Eve a medical anomaly to be studied? Or is she a demon who must be slain? Abandoned, left to their increasingly primal urges, the men divide into warring factions, some wanting to kill Eve, some to save her. Others exploit the chaos to wreak their own vengeance on new enemies. All turn to violence in a suddenly all-male world.Set in a small Appalachian town whose primary employer is a women�s prison, Sleeping Beauties is a wildly provocative, gloriously dramatic father-son collaboration that feels particularly urgent and relevant today.",
"PDF", 8.99, 14.99, 10.99, null),
(null, "978-0525954347", "The Punishment She Deserves", "Viking", "2017-03-20", 704, "Mystery", 
"The cozy, bucolic town of Ludlow is stunned when one of its most revered and respected citizens--Ian Druitt, the local deacon--is accused of a serious crime. Then, while in police custody, Ian is found dead. Did he kill himself? Or was he murdered?When Barbara Havers is sent to Ludlow to investigate the chain of events that led to Ian's death, all the evidence points to suicide. But Barbara can't shake the feeling that she's missing something. She decides to take a closer look at the seemingly ordinary inhabitants of Ludlow--mainly elderly retirees and college students--and discovers that almost everyone in town has something to hide.A masterful work of suspense, The Punishment She Deserves sets Detective Sergeant Barbara Havers and Inspector Thomas Lynley against one of their most intricate cases. Fans of the longtime series will love the many characters from Elizabeth George's previous novels who join Lynley and Havers, and readers new to the series will quickly see why she is one of the most popular and critically acclaimed writers of our time. Both a page-turner and a deeply complex story about the lies we tell, the lies we believe, and the redemption we need, this novel will be remembered as one of George's best.",
"PDF", 10.99, 15.99, 12.99, null),
(null, "978-0385659802", "The Curious Incident of the Dog in the Night-Time", "Anchor Canada", "2014-05-18 00:00:00", 240, "Mystery",
"Christopher John Francis Boone knows all the countries of the world and their capitals and every prime number up to 7,057. Although gifted with a superbly logical brain, Christopher is autistic. Everyday interactions and admonishments have little meaning for him. At fifteen, Christopher�s carefully constructed world falls apart when he finds his neighbour�s dog Wellington impaled on a garden fork, and he is initially blamed for the killing.

Christopher decides that he will track down the real killer, and turns to his favourite fictional character, the impeccably logical Sherlock Holmes, for inspiration. But the investigation leads him down some unexpected paths and ultimately brings him face to face with the dissolution of his parents� marriage. As Christopher tries to deal with the crisis within his own family, the narrative draws readers into the workings of Christopher�s mind.

And herein lies the key to the brilliance of Mark Haddon�s choice of narrator: The most wrenching of emotional moments are chronicled by a boy who cannot fathom emotions. The effect is dazzling, making for one of the freshest debut in years: a comedy, a tearjerker, a mystery story, a novel of exceptional literary merit that is great fun to read.",
"PDF", 7.99, 12.99, 10.99, null),
(null, "978-0762188598", "Postmortem", "Impress Pubns Ltd", "2000-11-01 00:00:00", 333, "Mystery", 
"A serial killer is on the loose in Richmond. Three women have died, brutalized and strangled in their own bedrooms. Kay Scarpetta, investigating the case, knows she must find some forensic evidence before the next killing. The author was previously an award-winning crime reporter.",
"PDF", 6.99, 10.99, 8.99, null),
(null, "978-0446696616", "1st to Die", "Grand Central Publishing", "2005-05-20 00:00:00", 432, "Mystery", 
"Imagine a killer who thinks, 'What is the worst thing anyone has ever done?--and then goes far beyond it. Now imagine four women --a police detective, an assistant DA, a reporter, and a medical examiner --who join forces as they sidestep their bosses to track down criminals. Known as the Women's Murder Club, they are pursuing a murderer whose twisted imagination has stunned an entire city. Their chief suspect is a socially prominent writer, but the men in charge won't touch him. On the trail of the most terrifying and unexpected killer ever, they discover a shocking surprise that turns everything about the case upside down.",
"PDF", 4.99, 9.99, 7.99, null),
(null, "978-0312353810", '"A" is for Alibi: A Kinsey Millhone Mystery', "St. Martin's Griffin", "2005-11-29 00:00:00", 320, "Mystery", 
"A IS FOR AVENGER
A tough-talking former cop, private investigator Kinsey Millhone has set up a modest detective agency in a quiet corner of Santa Teresa, California. A twice-divorced loner with few personal possessions and fewer personal attachments, she's got a soft spot for underdogs and lost causes.

A IS FOR ACCUSED
That's why she draws desperate clients like Nikki Fife. Eight years ago, she was convicted of killing her philandering husband. Now she's out on parole and needs Kinsey's help to find the real killer. But after all this time, clearing Nikki's bad name won't be easy.

A IS FOR ALIBI
If there's one thing that makes Kinsey Millhone feel alive, it's playing on the edge. When her investigation turns up a second corpse, more suspects, and a new reason to kill, Kinsey discovers that the edge is closer?and sharper?than she imagined.",
"PDF", 0.99, 2.99, 1.99, null),
(null, "978-1983639340", "The Mysterious Affair at Styles", "CreateSpace Independent Publishing Platform", "2018-01-07 00:00:00", 120, "Mystery",
"Who poisoned the wealthy Emily Inglethorp and how did the murderer penetrate and escape from her locked bedroom? Suspects abound in the quaint village of Styles St. Mary�from the heiress's fawning new husband to her two stepsons, her volatile housekeeper, and a pretty nurse who works in a hospital dispensary.

With impeccable timing, and making his unforgettable debut, the brilliant Belgian detective Hercule Poirot is on the case.",
"PDF", 0.2, 0.99, 0.5, null),
(null, "978-0385680400", "The Thirteenth Tale", "Anchor Canada", "2013-06-18 00:00:00", 416, "Mystery",
"Biographer Margaret Lea returns one night to her apartment above her father's antiquarian bookshop. On her steps she finds a letter. It is a hand-written request from one of Britain�s most prolific and well-loved novelists. Vida Winter, gravely ill, wants to recount her life story before it is too late, and she wants Margaret to be the one to capture her history. The request takes Margaret by surprise�she doesn�t know the author, nor has she read any of Miss Winter�s dozens of novels.

Late one night, while pondering whether to accept the task of recording Miss Winter�s personal story, Margaret begins to read her father�s rare copy of Miss Winter�s Thirteen Tales of Change and Desperation. She is spellbound by the stories and confused when she realizes the book only contains twelve stories. Where is the thirteenth tale? Intrigued, Margaret agrees to meet Miss Winter and act as her biographer.
 
As Vida Winter unfolds her story, she shares with Margaret the dark family secrets that she has long kept hidden as she remembers her days at Angelfield, the now burnt-out estate that was her childhood home. Margaret carefully records Miss Winter�s account and finds herself more and more deeply immersed in the strange and troubling story. In the end, both women have to confront their pasts and the weight of family secrets. As well as the ghosts that haunt them still.",
"PDF", 3.99, 9.99, 5.99, null),
(null, "978-0425228227", "Red Dragon", "Berkley", "2009-01-06 00:00:00", 464, "Mystery",
"A second family has been massacred by the terrifying serial killer the press has christened ?The Tooth Fairy.? Special Agent Jack Crawford turns to the one man who can help restart a failed investigation?Will Graham. Graham is the greatest profiler the FBI ever had, but the physical and mental scars of capturing Hannibal Lecter have caused Graham to go into early retirement. Now, Graham must turn to Lecter for help.",
"PDF", 3.99, 9.99, 5.99, null),
(null, "978-0947533175", "Sherlock Holmes and the Circus of Fear", "Breese Books Ltd", "1999-05-01 00:00:00", 112, "Mystery",
"After he learns of the tragic murder of the cunning and brilliant master of the most famous travelling circus in Europe, Lord George Sanger, Dr Watson recalls some escapades from the start of the 20th Century

Sherlock Holmes had been called in to help when Sanger starts to receive mysterious threats jeopardising the future of his circus and its animals.

The Great Detective and Dr Watson embark on another thrilling mission to uncover the mystery behind the Circus of Fear and race against the clock to catch the culprit and save as many lives as possible.

With disguises, secrets and elaborate plans, Holmes discovers that there is more to this circus than what meets the eye and the answer to the unsolved mystery is closer than what you might think.

Holmes and Watson are once again in the search of answers as they begin to investigate the murder of the retired Lord Sanger, nearly a decade since the incident at the circus.

With new-age inspectors on the scene, Holmes� investigation is blocked at every path and has he to dig deeper, beyond the elaborate and gory headlines.

Who is the mysterious murderer and why did he do it?

And what could Sanger have been hiding?

Not everything is as it seems; you shouldn�t always believe what you read in the papers.",
"MOBI", 0.2, 0.99, 0.5, null),
(null, "978-1542046596", "I Am Watching You", "Thomas & Mercer", "2017-10-01 00:00:00", 300, "Mystery", 
"When Ella Longfield overhears two attractive young men flirting with teenage girls on a train, she thinks nothing of it�until she realises they are fresh out of prison and her maternal instinct is put on high alert. But just as she�s decided to call for help, something stops her. The next day, she wakes up to the news that one of the girls�beautiful, green-eyed Anna Ballard�has disappeared.

A year later, Anna is still missing. Ella is wracked with guilt over what she failed to do, and she�s not the only one who can�t forget. Someone is sending her threatening letters�letters that make her fear for her life.

Then an anniversary appeal reveals that Anna�s friends and family might have something to hide. Anna�s best friend, Sarah, hasn�t been telling the whole truth about what really happened that night�and her parents have been keeping secrets of their own.

Someone knows where Anna is�and they�re not telling. But they are watching Ella.",
"MOBI", 0.5, 1.99, 0.99, null),
(null, "978-0307588371", "Gone Girl", "Broadway Books", "2014-04-22 00:00:00", 432, "Mystery", 
"On a warm summer morning in North Carthage, Missouri, it is Nick and Amy Dunne�s fifth wedding anniversary. Presents are being wrapped and reservations are being made when Nick�s clever and beautiful wife disappears. Husband-of-the-Year Nick isn�t doing himself any favors with cringe-worthy daydreams about the slope and shape of his wife�s head, but passages from Amy's diary reveal the alpha-girl perfectionist could have put anyone dangerously on edge. Under mounting pressure from the police and the media�as well as Amy�s fiercely doting parents�the town golden boy parades an endless series of lies, deceits, and inappropriate behavior. Nick is oddly evasive, and he�s definitely bitter�but is he really a killer? ",
"MOBI", 4.99, 10.99, 7.99, null),
(null, "978-0812976144", "The Alienist", "Random House Trade Paperbacks", "2006-10-24 00:00:00", 512, "Mystery",
"The year is 1896. The city is New York. Newspaper reporter John Schuyler Moore is summoned by his friend Dr. Laszlo Kreizler�a psychologist, or �alienist��to view the horribly mutilated body of an adolescent boy abandoned on the unfinished Williamsburg Bridge. From there the two embark on a revolutionary effort in criminology: creating a psychological profile of the perpetrator based on the details of his crimes. Their dangerous quest takes them into the tortured past and twisted mind of a murderer who will kill again before their hunt is over.",
"MOBI", 4.99, 9.99, 7.99, null),
(null, "978-0143034902", "The Shadow of the Wind", "Penguin Books", "2005-01-25 00:00:00", 512, "Mystery", 
"Barcelona, 1945: A city slowly heals in the aftermath of the Spanish Civil War, and Daniel, an antiquarian book dealer�s son who mourns the loss of his mother, finds solace in a mysterious book entitled The Shadow of the Wind, by one Juli�n Carax. But when he sets out to find the author�s other works, he makes a shocking discovery: someone has been systematically destroying every copy of every book Carax has written. In fact, Daniel may have the last of Carax�s books in existence. Soon Daniel�s seemingly innocent quest opens a door into one of Barcelona�s darkest secrets--an epic story of murder, madness, and doomed love.",
"MOBI", 9.99, 13.99, 11.99, null),
(null, "978-0544176560", "The Name of the Rose", "Mariner Books", "2014-04-22 00:00:00", 592, "Mystery", 
"The year is 1327. Franciscans in a wealthy Italian abbey are suspected of heresy, and Brother William of Baskerville arrives to investigate. When his delicate mission is suddenly overshadowed by seven bizarre deaths, Brother William turns detective. His tools are the logic of Aristotle, the theology of Aquinas, the empirical insights of Roger Bacon-all sharpened to a glistening edge by wry humor and a ferocious curiosity. He collects evidence, deciphers secret symbols and coded manuscripts,and digs into the eerie labyrinth of the abbey, where 'the most interesting things happen at night.'",
"MOBI", 7.99, 11.99, 9.99, null),
(null, "978-0743493468", "Angels & Demons", "Washington Square Press", "2006-05-23 00:00:00", 496, "Mystery",
"An ancient secret brotherhood. A devastating new weapon of destruction. An unthinkable target. When world-renowned Harvard symbologist Robert Langdon is summoned to his first assignment to a Swiss research facility to analyze a mysterious symbol�seared into the chest of a murdered physicist�he discovers evidence of the unimaginable: the resurgence of an ancient secret brotherhood known as the Illuminati...the most powerful underground organization ever to walk the earth. The Illuminati has now surfaced to carry out the final phase of its legendary vendetta against its most hated enemy�the Catholic Church.

Langdon�s worst fears are confirmed on the eve of the Vatican�s holy conclave, when a messenger of the Illuminati announces they have hidden an unstoppable time bomb at the very heart of Vatican City. With the countdown under way, Langdon jets to Rome to join forces with Vittoria Vetra, a beautiful and mysterious Italian scientist, to assist the Vatican in a desperate bid for survival.

Embarking on a frantic hunt through sealed crypts, dangerous catacombs, deserted cathedrals, and the most secretive vault on earth, Langdon and Vetra follow a 400-year-old trail of ancient symbols that snakes across Rome toward the long-forgotten Illuminati lair...a clandestine location that contains the only hope for Vatican salvation.",
"MOBI", 7.99, 10.99, 8.99, null),
(null, "978-0062490377", "And Then There Were None", "William Morrow Paperbacks", "2016-02-23 00:00:00", 272, "Mystery", 
"One of the most famous and beloved mysteries from The Queen of Suspense�Agatha Christie�now a Lifetime TV movie.

""Ten . . .""
Ten strangers are lured to an isolated island mansion off the Devon coast by a mysterious ""U. N. Owen.""

""Nine . . .""
At dinner a recorded message accuses each of them in turn of having a guilty secret, and by the end of the night one of the guests is dead.

""Eight . . .""
Stranded by a violent storm, and haunted by a nursery rhyme counting down one by one . . . as one by one . . . they begin to die.

""Seven . . .""
Which among them is the killer and will any of them survive?",
"MOBI", 6.99, 9.99, 7.99, null),
(null, "978-0857665508", "Nexus", "Angry Robot", "2015-03-03 00:00:00", 528, "Science Fiction", 
"In the near future, the experimental nano-drug Nexus can link humans together, mind to mind. There are some who want to improve it. There are some who want to eradicate it. And there are others who just want to exploit it.When a young scientist is caught improving Nexus, he�s thrust over his head into a world of danger and international espionage � for there is far more at stake than anyone realizes.From the halls of academe to the halls of power, from the headquarters of an elite US agency in Washington DC to a secret lab beneath a top university in Shanghai, from the underground parties of San Francisco to the illegal biotech markets of Bangkok, from an international neuroscience conference to a remote monastery in the mountains of Thailand � Nexus is a thrill ride through a future on the brink of explosion.",
"MOBI", 3.99, 8.99, 6.99, null),
(null, "978-0099560432", "Ready Player One", "Arrow", "2012-04-01 00:00:00", 384, "Science Fiction", 
"It's the year 2044, and the real world has become an ugly place. We're out of oil. We've wrecked the climate. Famine, poverty, and disease are widespread. Like most of humanity, Wade Watts escapes this depressing reality by spending his waking hours jacked into the OASIS, a sprawling virtual utopia where you can be anything you want to be, where you can live and play and fall in love on any of ten thousand planets. And like most of humanity, Wade is obsessed by the ultimate lottery ticket that lies concealed within this alternate reality: OASIS founder James Halliday, who dies with no heir, has promised that control of the OASIS - and his massive fortune - will go to the person who can solve the riddles he has left scattered throughout his creation. For years, millions have struggled fruitlessly to attain this prize, knowing only that the riddles are based in the culture of the late twentieth century. And then Wade stumbles onto the key to the first puzzle. Suddenly, he finds himself pitted against thousands of competitors in a desperate race to claim the ultimate prize, a chase that soon takes on terrifying real-world dimensions - and that will leave both Wade and his world profoundly changed.",
"MOBI", 4.99, 9.99, 7.99,  null),
(null, "978-0553380958", "Snow Crash", "Del Rey", "2000-05-02 00:00:00", 576, "Science Fiction", 
"In reality, Hiro Protagonist delivers pizza for Uncle Enzo�s CosoNostra Pizza Inc., but in the Metaverse he�s a warrior prince. Plunging headlong into the enigma of a new computer virus that�s striking down hackers everywhere, he races along the neon-lit streets on a search-and-destroy mission for the shadowy virtual villain threatening to bring about infocalypse.",
"MOBI", 6.99, 10.99, 9.99, null),
(null, "978-0553566062", "Virtual Light", "Spectra", "1994-07-01 00:00:00", 368, "Science Fiction",
"2005: Welcome to NoCal and SoCal, the uneasy sister-states of what used to be California. Here the millenium has come and gone, leaving in its wake only stunned survivors. In Los Angeles, Berry Rydell is a former armed-response rentacop now working for a bounty hunter. Chevette Washington is a bicycle messenger turned pickpocket who impulsively snatches a pair of innocent-looking sunglasses. But these are no ordinary shades. What you can see through these high-tech specs can make you rich--or get you killed. Now Berry and Chevette are on the run, zeroing in on the digitalized heart of DatAmerica, where pure information is the greatest high. And a mind can be a terrible thing to crash...",
"MOBI", 2.99, 8.99, 6.99, null),
(null, "978-1534303492", "Saga Volume 8", "Image Comics", "2018-01-02 00:00:00", 152, "Science Fiction", 
"After the traumatic events of the War for Phang, Hazel, her parents, and their surviving companions embark on a life-changing adventure at the westernmost edge of the universe.",
"MOBI", 9.99, 19.99, 13.99, null),
(null, "978-0141036144", "Ninteneen Eighty Four", "Penguin UK", "2008-07-29 00:00:00", 336, "Science Fiction",
"Winston Smith works for the Ministry of Truth in London, chief city of Airstrip One. Big Brother stares out from every poster, the Thought Police uncover every act of betrayal. When Winston finds love with Julia, he discovers that life does not have to be dull and deadening, and awakens to new possibilities.",
"MOBI", 10.79, 25.82, 22.82, null),
(null, "978-0771008795", "The Handmaid's Tale", "Emblem Editions", "2011-09-06 00:00:00", 368, "Science Fiction", 
"In this multi-award-winning, bestselling novel, Margaret Atwood has created a stunning Orwellian vision of the near future. This is the story of Offred, one of the unfortunate �Handmaids� under the new social order who have only one purpose: to breed. In Gilead, where women are prohibited from holding jobs, reading, and forming friendships, Offred�s persistent memories of life in the �time before� and her will to survive are acts of rebellion. Provocative, startling, prophetic, and with Margaret Atwood�s devastating irony, wit, and acute perceptive powers in full force, The Handmaid�s Tale is at once a mordant satire and a dire warning.",
"MOBI", 11.99, 17.95, 12.37, null),
(null, "978-0553448122", "Artemis: A Novel", "Crown", "2017-11-14 00:00:00", 320, "Science Fiction", 
"Jasmine Bashara never signed up to be a hero. She just wanted to get rich. 
 
Not crazy, eccentric-billionaire rich, like many of the visitors to her hometown of Artemis, humanity�s first and only lunar colony. Just rich enough to move out of her coffin-sized apartment and eat something better than flavored algae. Rich enough to pay off a debt she�s owed for a long time.
 
So when a chance at a huge score finally comes her way, Jazz can�t say no. Sure, it requires her to graduate from small-time smuggler to full-on criminal mastermind. And it calls for a particular combination of cunning, technical skills, and large explosions�not to mention sheer brazen swagger. But Jazz has never run into a challenge her intellect can�t handle, and she figures she�s got the �swagger� part down. 
 
The trouble is, engineering the perfect crime is just the start of Jazz�s problems. Because her little heist is about to land her in the middle of a conspiracy for control of Artemis itself. 
 
Trapped between competing forces, pursued by a killer and the law alike, even Jazz has to admit she�s in way over her head. She�ll have to hatch a truly spectacular scheme to have a chance at staying alive and saving her city. 
 
Jazz is no hero, but she is a very good criminal. 
 
That�ll have to do. ",
"MOBI", 15.99, 36, 23.99, null),
(null, "978-0316332835", "Persepolis Rising (The Expanse)", "Orbit", "2017-12-05 00:00:00", 560, "Science Fiction", 
"
AN OLD ENEMY RETURNS

In the thousand-sun network of humanity's expansion, new colony worlds are struggling to find their way. Every new planet lives on a knife edge between collapse and wonder, and the crew of the aging gunship Rocinante have their hands more than full keeping the fragile peace.

In the vast space between Earth and Jupiter, the inner planets and belt have formed a tentative and uncertain alliance still haunted by a history of wars and prejudices. On the lost colony world of Laconia, a hidden enemy has a new vision for all of humanity and the power to enforce it.

New technologies clash with old as the history of human conflict returns to its ancient patterns of war and subjugation. But human nature is not the only enemy, and the forces being unleashed have their own price. A price that will change the shape of humanity -- and of the Rocinante -- unexpectedly and forever...",
"MOBI", 18.99, 36.5, 30.32, null),
(null, "978-0141036137", "Animal Farm", "Penguin UK", "2008-07-29 00:00:00", 112, "Science Fiction", "Renowned urban artist Shepard Fairey's new look for Orwell's timeless satire 'All animals are equal. But some animals are more equal than others.' Mr Jones of Manor Farm is so lazy and drunken that one day he forgets to feed his livestock. The ensuing rebellion under the leadership of the pigs Napoleon and Snowball leads to the animals taking over the farm. Vowing to eliminate the terrible inequities of the farmyard, the renamed Animal Farm is organised to benefit all who walk on four legs. But as time passes, the ideals of the rebellion are corrupted, then forgotten. And something new and unexpected emerges. . . Animal Farm - the history of a revolution that went wrong - is George Orwell's brilliant satire on the corrupting influence of power.",
"MOBI", 5.99, 11.99, 10.79, null),
(null, "978-0316547611", "The Power", "Little, Brown and Company", "2017-10-10 00:00:00", 400, "Science Fiction", 
"In THE POWER, the world is a recognizable place: there's a rich Nigerian boy who lounges around the family pool; a foster kid whose religious parents hide their true nature; an ambitious American politician; a tough London girl from a tricky family. But then a vital new force takes root and flourishes, causing their lives to converge with devastating effect. Teenage girls now have immense physical power--they can cause agonizing pain and even death. And, with this small twist of nature, the world drastically resets.

From award-winning author Naomi Alderman, THE POWER is speculative fiction at its most ambitious and provocative, at once taking us on a thrilling journey to an alternate reality, and exposing our own world in bold and surprising ways.",
"MOBI", 17.99, 34, 28.83, null),
(null, "978-0765393135", "Binti: The Night Masquerade", "Tor Books", "2018-01-16 00:00:00", 208, "Science Fiction", 
"Binti has returned to her home planet, believing that the violence of the Meduse has been left behind. Unfortunately, although her people are peaceful on the whole, the same cannot be said for the Khoush, who fan the flames of their ancient rivalry with the Meduse.

Far from her village when the conflicts start, Binti hurries home, but anger and resentment has already claimed the lives of many close to her.

Once again it is up to Binti, and her intriguing new friend Mwinyi, to intervene--though the elders of her people do not entirely trust her motives--and try to prevent a war that could wipe out her people, once and for all.",
"MOBI", 7.99, 19.5, 17.99, null),
(null, "978-0765386632", "Death's End", "Tor Books", "2017-09-05", 608, "Science Fiction", 
"With The Three-Body Problem, English-speaking readers got their first chance to read China's most beloved science fiction author, Cixin Liu. The Three-Body Problem was released to great acclaim including coverage in The New York Times and The Wall Street Journal and reading list picks by Barack Obama and Mark Zuckerberg. It was also won the Hugo and Nebula Awards, making it the first translated novel to win a major SF award.

Now this epic trilogy concludes with Death's End. Half a century after the Doomsday Battle, the uneasy balance of Dark Forest Deterrence keeps the Trisolaran invaders at bay. Earth enjoys unprecedented prosperity due to the infusion of Trisolaran knowledge. With human science advancing daily and the Trisolarans adopting Earth culture, it seems that the two civilizations will soon be able to co-exist peacefully as equals without the terrible threat of mutually assured annihilation. But the peace has also made humanity complacent.

Cheng Xin, an aerospace engineer from the early twenty-first century, awakens from hibernation in this new age. She brings with her knowledge of a long-forgotten program dating from the beginning of the Trisolar Crisis, and her very presence may upset the delicate balance between two worlds. Will humanity reach for the stars or die in its cradle?",
"MOBI", 10.99, 23.99, 21.33, null),
(null, "978-0765386694", "The Dark Forest", "Tor Books", "2016-08-16", 512, "Science Fiction", 
"In The Dark Forest, Earth is reeling from the revelation of a coming alien invasion-in just four centuries' time. The aliens' human collaborators may have been defeated, but the presence of the sophons, the subatomic particles that allow Trisolaris instant access to all human information, means that Earth's defense plans are totally exposed to the enemy. Only the human mind remains a secret. This is the motivation for the Wallfacer Project, a daring plan that grants four men enormous resources to design secret strategies, hidden through deceit and misdirection from Earth and Trisolaris alike. Three of the Wallfacers are influential statesmen and scientists, but the fourth is a total unknown. Luo Ji, an unambitious Chinese astronomer and sociologist, is baffled by his new status. All he knows is that he's the one Wallfacer that Trisolaris wants dead.",
"MOBI", 10.99, 22.5, 20.25, null),
(null, "978-0756414146", "Emergence", "DAW", "2018-01-02 00:00:00", 336, "Science Fiction", 
"Bren Cameron, acting as the representative of the atevi's political leader, Tabini-aiji, as well as translator between humans and atevi, has undertaken a mission to the human enclave of Mospheira. Both his presence on the island and his absence from the continent have stirred old enemies to realize new opportunities.
 
Old hatreds. Old grudges. Old ambitions.
 
The situation has strengthened the determination of power-seekers on both sides of the strait. Bren knows most of them very well, but not all of them well enough. The space station on which the world increasingly relies is desperate to get more supplies up to orbit and to get a critical oversupply of human refugees down to the world below. Rationing is in force on the station, but the overpopulation problem has to be solved quickly�and Bren's mission on Mospheira has expanded to include preparation for that landing.
 
First down will be the three children to whom Tabini's son has a close connection. But following them will be thousands of humans who have never set foot on a planet, humans descended from colonists and officers who split off from Mospheiran humans two hundred years before in a bitter parting of the ways. There is no way the atevi, native to the world, will cede any more land to these new arrivals: they will have to share the island. But certain Mospheirans are willing to use force to prevent these refugees from settling among them.
 
Bren's job is as general peacemaker�but old enemies want war. Is Bren's diplomatic acumen enough to prevent a war that both sides are prepared to wage?",
"MOBI", 14.99, 35, 30.69, null),
(null, "978-0312536633", "The Forever War", "St. Martin's Griffin", "2009-02-17", 228, "Science Fiction", 
"The Earth's leaders have drawn a line in the interstellar sand--despite the fact that the fierce alien enemy they would oppose is inscrutable, unconquerable, and very far away. A reluctant conscript drafted into an elite Military unit, Private William Mandella has been propelled through space and time to fight in the distant thousand-year conflict; to perform his duties and do whatever it takes to survive the ordeal and return home. But 'home' may be even more terrifying than battle, because, thanks to the time dilation caused by space travel, Mandella is aging months while the Earth he left behind is aging centuries...",
"MOBI", 10.99, 18.5, 10.99, null),
(null, "978-1101972120", "Stories of Your Life and Others", "Vintage", "2016-05-14", 304, "Science Fiction", 
"Stories of Your Life and Others delivers dual delights of the very, very strange and the heartbreakingly familiar, often presenting characters who must confront sudden change�the inevitable rise of automatons or the appearance of aliens�with some sense of normalcy. With sharp intelligence and humor, Chiang examines what it means to be alive in a world marked by uncertainty, but also by beauty and wonder. An award-winning collection from one of today's most lauded writers, Stories of Your Life and Others is a contemporary classic.",
"MOBI", 13.99, 22, 16.82, null),
(null, "978-1443423656", "The Humans", "HarperCollins", "2013-07-02", 304, "Science Fiction", 
"The bestselling, award-winning author of The Radleys is back with what may be his best, funniest, and most devastating dark comedy yet. When an extraterrestrial visitor arrives on Earth, his first impressions of the human species are less than positive. Taking the form of Professor Andrew Martin, a prominent mathematician at

Cambridge University, the visitor is eager to complete the gruesome task assigned him and hurry back home to the utopian world of his own planet, where everyone enjoys immortality and infinite knowledge.

He is disgusted by the way humans look, what they eat, and their capacity for murder and war, and he is equally baffled by the concepts of love and family. But as time goes on, he starts to realize there may be more to this weird species than he has been led to believe. Disguised as Martin, he drinks wine, reads poetry, and develops an ear for rock music and a taste for peanut butter. Slowly, unexpectedly, he forges bonds with Martin�s family, and in picking up the pieces of the professor�s shattered personal life, he begins to see hope and beauty in the humans� imperfections and to question the mission that brought him here.

Praised by The New York Times as a �novelist of great seriousness and talent,� Matt Haig delivers an unlikely story about human nature and the joy found in the messiness of life on Earth. The Humans is a funny, compulsively readable tale that playfully and movingly explores the ultimate subject�ourselves.",
"MOBI", 11.99, 16.99, 13.99, null),
(null, "978-0441478125", "The Left Hand of Darkness", "Ace", "2000-07-01", 352, "Science Fiction", 
"A lone human ambassador is sent to Winter, an alien world without sexual prejudice, where the inhabitants can change their gender whenever they choose. His goal is to facilitate Winter's inclusion in a growing intergalactic civilization. But to do so he must bridge the gulf between his own views and those of the strange, intriguing culture he encounters...

Embracing the aspects of psychology, society, and human emotion on an alien world, The Left Hand of Darkness stands as a landmark achievement in the annals of intellectual science fiction.",
"MOBI", 10.99, 17, 15.36, null),
(null, "978-0441783588", "Starship Troopers", "Ace", "1987-05-15", 352, "Science Fiction", 
"In one of Robert Heinlein's most controversial bestsellers, a recruit of the future goes through the toughest boot camp in the Universe--and into battle with the Terran Mobile Infantry against mankind's most frightening enemy.",
"MOBI", 10.99, 26.21, 20.52, null),
(null, "978-1400025688", "Written in My Own Heart's Blood", "Seal Books","2016-05-31", 1152, "Romance", 
"1778: France declares war on Great Britain, the British army leaves Philadelphia, and George Washington�s troops leave Valley Forge in pursuit. At this moment, Jamie Fraser returns from a presumed watery grave to discover that his best friend has married his wife, his illegitimate son has discovered (to his horror) who his father really is, and his beloved nephew, Ian, wants to marry a Quaker. Meanwhile, Jamie�s wife, Claire, and his sister, Jenny, are busy picking up the pieces.The Frasers can only be thankful that their daughter Brianna and her family are safe in twentieth-century Scotland. Or not. In fact, Brianna is  searching for her own son, who was kidnapped by a man determined to learn her family�s secrets. Her husband, Roger, has ventured into the past in search of the missing boy . . . never suspecting that the object of his quest has not left the present. Now, with Roger out of the way, the kidnapper can focus on his true target: Brianna herself.",
"MOBI", 6.99, 13.99, 8.99, null),
(null, "978-0764212956", "Still Life", "BETHANY HOUSE", "2017-01-26", 352, "Romance", 
"Work hits too close to home for crime scene photographer Avery Tate when her best friend disappears. The only lead is a chilling photo of her apparently dead. As Avery, her boss Parker, and his friends dig into the case, she's forced to confront her feelings for Parker when they come face-to-face with a dangerous criminal.",
"MOBI", 2.99, 10.99, 4.99, null),
(null, "978-0525524984", "Hardcore Twenty-Four", "Random House Large Print", "2017-11-14", 336, "Romance", 
"Trouble comes in bunches for Stephanie Plum. First, professional grave robber and semi-professional loon, Simon Diggery, won�t let her take him in until she agrees to care for his boa constrictor, Ethel. Stephanie�s main qualification for babysitting an extremely large snake is that she owns a stun gun�whether that�s for use on the wandering serpent or the petrified neighbors remains to be seen.Events take a dark turn when headless bodies start appearing across town. At first, it�s just corpses from a funeral home and the morgue that have had the heads removed. But when a homeless man is murdered and dumped behind a church Stephanie knows that she�s the only one with a prayer of catching this killer.If all that�s not enough, Diesel�s back in town. The 6-foot-tall, blonde-haired hunk is a man who accepts no limits�that includes locked doors, closed windows and underwear. Trenton�s hottest cop, Joe Morelli isn�t pleased at this unexpected arrival nor is Ranger, the high-powered security consultant who has his own plans for Stephanie.As usual Jersey�s favorite bounty hunter is stuck in the middle with more questions than answers. What�s the deal with Grandma Mazur�s latest online paramour? Who is behind the startling epidemic of mutilated corpses? And is the enigmatic Diesel�s sudden appearance a coincidence or the cause of recent deadly events?  ",
"PDF", 5.99, 15.99, 10.99, null),
(null, "978-0749020002", "The Honeyfield Bequest", "Allison & Busby", "2016-11-21", 320, "Romance", 
"1901, Wiltshire: Kathleen's family are determined to force her into marriage with a violent man. In desperation she runs away and finds what consolation she can for her lost dreams. Then tragedy strikes and Kathleen seeks refuge at Honeyfield House, where a charitable bequest has created a hiding place for families in trouble. Nathan is attracted to Kathleen, but when his father dies suddenly, leaving the business in chaos, he can't court anyone. Godfrey Seaton is still following her and she isn't sure whose side her father is on. When Godfrey strikes, Nathan can't stand back when she needs help. Can the two of them defeat him and make a new life together?",
"PDF", 1.99, 4.99, 3.99, null),
(null, "978-1410493309", "Island of Glass", "Thorndike Press", "2016-12-07", 488, "Romance", 
"As the hunt for the Star of Ice leads the six guardians to Ireland, Doyle, the immortal, must face his tragic past. Three centuries ago, he closed off his heart, yet his warrior spirit is still drawn to the wild. And there�s no one more familiar with the wild than Riley ? and the wolf within her . . .",
"PDF", 6.99, 13.99, 10.99, null),
(null, "978-1540462497", "The Gender Game 3: The Gender Lie (Volume 3)", "CreateSpace Independent Publishing Platform", "2016-12-31", 408, "Romance",
"I'd want you to help me win a war...

After discovering the shocking secret buried deep within The Green, Violet has a grave decision to make. 
Trust the woman who saved her, or not?

So far, everything about the woman has taken Violet by surprise. Her behavior has been honest and upfront - a refreshing change for Violet. Besides, with one of her loved ones hanging on life support, and the other close to falling completely out of reach, Violet doesn't exactly have a lot of choice.  

Her only way forward is to embark on a dicey excursion. For this, she knows she needs the Liberators' help - and must be prepared to take whatever risks that comes with. 

But what neither she, nor any of her new Liberator comrades can prepare for, is just how deep the lies of their homelands run...'",
"PDF", 0.45, 1.6, 1.25, null),
(null, "978-0996135696", "Midnight Blue", " LJ Shen", "2018-01-16", 338, "Romance", 
"It should have been easy. I needed the money. He needed a babysitter to keep him from snorting himself to death. I was cherry-picked especially for him. Responsible. Optimistic. Warm. Innocent. The worst part is that I should have known better. Alex Winslow. British rock star. Serial heartbreaker. Casanova with whiskey eyes. �Don�t get near the devil in a leather jacket. He�ll chew you up and spit you out.� Guess what? I didn�t listen. I signed the contract. World tour. Three months. Four Continents. One hundred shows. My name is Indigo Bellamy, and I sold my soul to a tattooed god. Problem was, my soul wasn�t enough for Alex Winslow. He ended up taking my body, too. Then he took my heart. Then he took my all.", 
"PDF", 0.75, 2.98, 1.85, null),
(null, "978-1942215707", "Sex, Not Love", "Vi Keeland", "2018-01-15", 300, "Romance", 
"My relationship with Hunter Delucia started backwards.

We met at a wedding�him sitting on the groom�s side, me sitting on the bride�s. Stealing glances at each other throughout the night, there was no denying an intense, mutual attraction.

I caught the bouquet; he caught the garter. Hunter held me tightly while we danced and suggested we explore the chemistry sparking between us. His blunt, dirty mouth should�ve turned me off. But for some crazy reason, it had the opposite effect on me.

We ended up back in my hotel room. The next morning, I headed home to New York leaving him behind in California with the wrong number.

I thought about him often, but after my last relationship, I�d sworn off of charming, cocky, gorgeous-as-sin men. A year later, Hunter and I met again at the birth of our friends� baby. Our attraction hadn't dulled one bit. After a whirlwind trip, he demanded a real phone number this time. So I left him with my mother�s�she could scare away any man with her talks of babies and marriage�and flew back home.

I�d thought it was funny, until the following week when he rang the bell at Mom�s house for Sunday night dinner. The crazy, gorgeous man had won over my mother and taken an eight-week assignment in my city. He proposed we spend that time screwing each other out of our systems.

Eight weeks of mind-blowing sex with no strings attached? What did I have to lose?


Nothing, I thought. 
It�s just sex, not love. 
But you know what they say about the best laid plans�",
"PDF", 0.99, 3.99, 2.25, null),
(null, "978-1682308585", "STRIKE (Gentry Generations)", "EverAfter Romance", "2017-08-21", 228, "Romance", 
"The story of the Gentry family continues in this sexy new spinoff series from the NYT and USA Today bestselling author of the Gentry Boys series.",
"PDF", 0.18, 1.01, 0.75, null),
(null, "978-1545459522", "Mixed Up", "CreateSpace Independent Publishing Platform", "2017-04-19", 270, "Romance", 
"Dirty cocktails, deadly enemies with a red-hot attraction, and one big, crazy, Greek family--what could possibly go wrong? Hiring my brother's best friend was not on my to-do list. Neither was he. Expanding my dirty cocktail bar into food was supposed to be easy, except finding a chef in my little town of Whiskey Key is anything but. Until Parker Hamilton comes home--bringing his Michelin starred chef's hat with him. He has no work. I need someone like him in my new kitchen. There's just one problem: I hate his cocky, filthy-mouthed, sexy-as-hell guts. Even if I might want him. Just a little... Working for my best friend's sister? Not on my to-do list. She's another story. Whiskey Key was supposed to be a relaxing vacation, except I haven't reached the heights I have by lying in a hammock drinking cocktails. So when Raven Archer is desperate for a chef, I offer up my skills. I'm bored. She needs what I can give her. Except there's a problem: I've always hated her. Her and her big, blue eyes, sassy mouth, and killer curves. If only I didn't want her.",
"PDF", 1.25, 5.09, 4.5, null),
(null, "978-1544237329", "The Low of Love (Wayward Love) (Volume 2)", "CreateSpace Independent Publishing Platform", "2017-04-19", 270, "Romance",
"Dirty cocktails, deadly enemies with a red-hot attraction, and one big, crazy, Greek family--what could possibly go wrong? Hiring my brother's best friend was not on my to-do list. Neither was he. Expanding my dirty cocktail bar into food was supposed to be easy, except finding a chef in my little town of Whiskey Key is anything but. Until Parker Hamilton comes home--bringing his Michelin starred chef's hat with him. He has no work. I need someone like him in my new kitchen. There's just one problem: I hate his cocky, filthy-mouthed, sexy-as-hell guts. Even if I might want him. Just a little... Working for my best friend's sister? Not on my to-do list. She's another story. Whiskey Key was supposed to be a relaxing vacation, except I haven't reached the heights I have by lying in a hammock drinking cocktails. So when Raven Archer is desperate for a chef, I offer up my skills. I'm bored. She needs what I can give her. Except there's a problem: I've always hated her. Her and her big, blue eyes, sassy mouth, and killer curves. If only I didn't want her.",
"PDF", 1.21, 6.24, 4.75, null),
(null, "978-1981681419", "Exposed: A Bad Boy Contemporary Romance", "CreateSpace Independent Publishing Platform", "2017-12-12", 260, "Romance",
"Wanna know a secret? I�ve never had an orga*m. One night, everything changes. I lose my innocence, and I have my first O. With a man who probably doesn�t care. Maxwell Brideau. Ruthless. Billionaire. Single father. He�s my boss�s brother and he doesn�t date. He�s never been seen with the same woman twice. I�d be foolish to get my hopes up. I�m crazy about his little girl. Bella desperately needs a mother-figure. What does Maxwell need? A marriage of convenience. He wants me to marry him and take care of his daughter. I watch over Bella during the day. My nights belong to Maxwell. It�s an arrangement, nothing more. An arrangement with benefits. I�m helpless beneath his touch. He owns me, body and soul, and is going to show me many more filthy firsts... Exposed is a STAND-ALONE, full-length contemporary romance. There are no cliffhangers, and a happy ending is guaranteed!",
"PDF", 0.23, 1, 0.75, null),
(null, "978-1982022488", "Damaged", "CreateSpace Independent Publishing Platform", "2017-12-25", 264, "Romance", 
"10 years ago, I lost her. 
Now only Charlotte could melt my cold heart. 

I made my billions, and then I built my fortress. 
I was alone on my mountain. 
Till she came. 
I saved her, but she can�t remember her past. 
And I can�t forget mine. 
The winter storm trapped us together. 
But I�ll keep her safe and warm. 
With every caress, I'll show her body what it craves.
With every kiss, I'll mark her as my woman. 
I know someone hurt her. 
And that b@stard wants her back. 
He doesn�t know who the f*ck he�s messing with. 
Charlotte is mine now, and I�ll protect her no matter what. 

But will she stay mine when her memories return?",
"PDF", 0.24, 1.01, 0.76, null),
(null, "978-1682814505", "Her Secret Ranger", "Entangled Publishing", "2017-03-06", 230, "Romance", 
"Kissing the sexy soldier was a dare she couldn�t resist�

By-the-book event planner Beth Brannigan�s best friend dared her to kiss a cowboy. She should have said no. Instead, she said please�again and again. If her brother finds out she�s dating�okay, kissing�okay, sleeping with�one of his military buddies, he�ll kill her. Assuming he doesn�t kill his friend first.

Former Army Ranger Brick Mitchum isn�t a relationship kind of guy. But then he meets Beth and starts to wonder if maybe it�s time he settled down. She�s mysterious. Unpredictable. Curvy in every way he needs� And hiding something. He�s just got to figure out what. ",
"PDF", 0.14, 0.93, 0.7, null),
(null, "978-0525501268", "Past Perfect: A Novel", "Random House Large Print; Large Print edition", "2017-11-28", 384, "Romance", 
"Sybil and Blake Gregory have established a predictable, well-ordered Manhattan life�she as a cutting-edge design authority and museum consultant, he in high-tech investments�raising their teenagers Andrew and Caroline and six-year-old Charlie. But everything changes when Blake is offered a dream job he can�t resist as CEO of a start-up in San Francisco. He accepts it without consulting his wife and buys a magnificent, irresistibly underpriced historic Pacific Heights mansion as their new home.

The past and present suddenly collide for them in the elegant mansion filled with tender memories and haunting portraits when an earthquake shocks them the night they arrive. The original inhabitants appear for a few brief minutes. In the ensuing days, the Gregorys meet the large and lively family who lived there a century ago: distinguished Bertrand Butterfield and his gracious wife Gwyneth, their sons Josiah and little Magnus, daughters Bettina and Lucy, formidable Scottish matriarch Augusta and her eccentric brother Angus.

All long since dead. All very much alive in spirit�and visible to the Gregorys and no one else. The two families are delighted to share elegant dinners and warm friendship. They have much to teach each other, as the Gregorys watch the past unfold while living their own modern-day lives. Within these enchanted rooms, it is at once 1917 and a century later, where the Gregorys gratefully realize they have been given a perfect gift�beloved friends and the wisdom to shape their own future with grace from a fascinating past.", 
"PDF", 9.99, 15.68, 13.75, null),
(null, "978-1786811271", "The Silent Wife", "Bookouture", "2017-02-24", 350, "Romance", 
"Would you risk everything for the man you loved? Even if you knew he'd done something terrible?
'A heart-wrenching and gripping tale. I was hooked from the very first page.' Write Escape

Lara�s life looks perfect on the surface. Gorgeous doting husband Massimo, sweet little son Sandro and the perfect home. Lara knows something about Massimo. Something she can�t tell anyone else or everything he has worked so hard for will be destroyed: his job, their reputation, their son. This secret is keeping Lara a prisoner in her marriage.

Maggie is married to Massimo�s brother Nico and lives with him and her troubled stepdaughter. She knows all of Nico�s darkest secrets � or so she thinks. Then one day she discovers a letter in the attic which reveals a shocking secret about Nico�s first wife. Will Maggie set the record straight or keep silent to protect those she loves?

For a family held together by lies, the truth will come at a devastating price.",
"PDF", 0.95, 3.99, 2.1, null),
(null, "978-1476729091", "The Rosie Project: A Novel", "Simon & Schuster; Reprint edition", "2014-06-03", 295, "Romance", 
"The art of love is never a science: Meet Don Tillman, a brilliant yet socially inept professor of genetics, who�s decided it�s time he found a wife. In the orderly, evidence-based manner with which Don approaches all things, he designs the Wife Project to find his perfect partner: a sixteen-page, scientifically valid survey to filter out the drinkers, the smokers, the late arrivers. 

Rosie Jarman possesses all these qualities. Don easily disqualifies her as a candidate for The Wife Project (even if she is �quite intelligent for a barmaid�). But Don is intrigued by Rosie�s own quest to identify her biological father. When an unlikely relationship develops as they collaborate on The Father Project, Don is forced to confront the spontaneous whirlwind that is Rosie?and the realization that, despite your best scientific efforts, you don�t find love, it finds you.",
"PDF", 4.99, 10.97, 8.75, null),
(null, "978-1455520619", "See Me", "Grand Central Publishing; First Edition edition", "2015-10-13", 496, "Romance", 
"See me just as I see you . . .

Colin Hancock is giving his second chance his best shot. With a history of violence and bad decisions behind him and the threat of prison dogging his every step, he's determined to walk a straight line. To Colin, that means applying himself single-mindedly toward his teaching degree and avoiding everything that proved destructive in his earlier life. Reminding himself daily of his hard-earned lessons, the last thing he is looking for is a serious relationship.

Maria Sanchez, the hardworking daughter of Mexican immigrants, is the picture of conventional success. With a degree from Duke Law School and a job at a prestigious firm in Wilmington, she is a dark-haired beauty with a seemingly flawless professional track record. And yet Maria has a traumatic history of her own, one that compelled her to return to her hometown and left her questioning so much of what she once believed.

A chance encounter on a rain-swept road will alter the course of both Colin and Maria's lives, challenging deeply held assumptions about each other and ultimately, themselves. As love unexpectedly takes hold between them, they dare to envision what a future together could possibly look like . . . until menacing reminders of events in Maria's past begin to surface.

As a series of threatening incidents wreaks chaos in Maria's life, Maria and Colin will be tested in increasingly terrifying ways. Will demons from their past destroy the tenuous relationship they've begun to build, or will their love protect them, even in the darkest hour?

Rich in emotion and fueled with suspense, SEE ME reminds us that love is sometimes forged in the crises that threaten to shatter us . . . and that those who see us for who we truly are may not always be the ones easiest to recognize.",
"PDF", 2.55, 8.16, 6.25, null),
(null, "978-1981689798", "Cabin Fever: A Mountain Man Romance", "CreateSpace Independent Publishing Platform", "2017-12-14", 534, "Romance","A Mountain Man Romance",
"PDF", 0.22, 1.02, 0.83, null),
(null, "978-1542048439", "Infraction (Players Game Book 2)", "Skyscape", "2018-01-09", 284, "Romance", 
"Pro footballer Miller Quinton would do anything for his best friend and teammate�including �fake dating� his friend�s sister. What no one knows is that seven months ago in Vegas, Miller and Kinsey did a whole lot more than just kiss. Miller knows that this cheerleader is off-limits to him and any guy on the team. Still, he can�t stop himself.

Kinsey�s whole world is on the verge of crumbling. Her dad has cancer. Her overprotective brother is falling apart. Dating Miller may be a fake-out, but he�s the one guy who can make her forget about everything�including all the reasons she stayed away from football players. With each heated moment, Miller feels more like a safe place�even though he�s not safe at all.

Now temptation is testing every rule in the game of love. But how long can they go on playing when winning is a harder goal than either of them imagined?",
"PDF", 1.45, 3.99, 2.44, null),
(null, "978-1443453516", "Killer: My Life in Hockey", "HarperCollins", "2017-10-17", 336, "Biographies", 
"Doug Gilmour didn�t look fearsome on a pair of skates�being an �undersized� forward would plague him during his early career�but few players matched his killer instincts in the faceoff circle or in front of the net. The Hockey Hall of Famer from Kingston, Ontario, played for seven teams over his twenty-year career, netting 450 goals and 964 assists during the regular season and another 188 points in the playoffs, making him one of the highest-scoring centres of all time. Gilmour played a big role in the 1989 Stanley Cup victory, scoring the winning goal for the Calgary Flames. Perhaps most famously, he led the Toronto Maple Leafs to multiple winning seasons and in 1993, took them to the brink of their first Stanley Cup final in decades, only to lose out on one of the most controversial calls in hockey history.In Killer, Doug Gilmour bares all about his on- and off-the-ice exploits and escapades. Gilmour has always been frank with the media, and his memoir is as revealing as it is hilarious. He played with the greatest players of his generation, and his love for the game and for life are legendary.",
"PDF", 10.99, 16.99, 14.99, null),
(null, "978-0062301239", "Elon Musk: Tesla, SpaceX, and the Quest for a Fantastic Future", "Ecco", "2015-05-19", 400, "Biographies", 
"In Elon Musk: Tesla, SpaceX, and the Quest for a Fantastic Future, veteran technology journalist Ashlee Vance provides the first inside look into the extraordinary life and times of Silicon Valley's most audacious entrepreneur. Written with exclusive access to Musk, his family and friends, the book traces the entrepreneur's journey from a rough upbringing in South Africa to the pinnacle of the global business world. Vance spent more than 30 hours in conversation with Musk and interviewed close to 300 people to tell the tumultuous stories of Musk's world-changing companies: PayPal, Tesla Motors, SpaceX and SolarCity, and to characterize a man who has renewed American industry and sparked new levels of innovation while making plenty of enemies along the way.",
"PDF", 6.99, 11.99, 9.99, null),
(null, "978-1451648539", "Steve Jobs", "Simon & Schuster", "2011-10-24", 656, "Biographies", 
"Based on more than forty interviews with Jobs conducted over two years�as well as interviews with more than a hundred family members, friends, adversaries, competitors, and colleagues�Walter Isaacson has written a riveting story of the roller-coaster life and searingly intense personality of a creative entrepreneur whose passion for perfection and ferocious drive revolutionized six industries: personal computers, animated movies, music, phones, tablet computing, and digital publishing.",
"PDF", 7.99, 12.99, 10.99, null),
(null, "978-0062413406", "Alibaba: The House That Jack Ma Built", "Ecco", "2016-04-12", 304, "Biographies", 
"In just a decade and half Jack Ma, a man from modest beginnings who started out as an English teacher, founded and built Alibaba into one of the world�s largest companies, an e-commerce empire on which hundreds of millions of Chinese consumers depend. Alibaba�s $25 billion IPO in 2014 was the largest global IPO ever. A Rockefeller of his age who is courted by CEOs and Presidents around the world, Jack is an icon for China�s booming private sector and the gatekeeper to hundreds of millions of middle class consumers.",
"PDF", 9.99, 17.99, 13.99, null),
(null, "978-1501139154", "Leonardo Da Vinci", "Simon & Schuster", "2017-10-17", 624, "Biographies", 
"Based on thousands of pages from Leonardo�s astonishing notebooks and new discoveries about his life and work, Walter Isaacson weaves a narrative that connects his art to his science. He shows how Leonardo�s genius was based on skills we can improve in ourselves, such as passionate curiosity, careful observation, and an imagination so playful that it flirted with fantasy.",
"PDF", 12.99, 18.99, 16.99, null),
(null, "978-0544217621", "Johnny Carson", "Eamon Dolan/Mariner Books", "2014-10-07", 320, "Biographies", 
"Henry Bushkin was Carson�s best friend and lawyer during that period, and his book is a tautly rendered and remarkably nuanced portrait of Carson, revealing not only how he truly was, but why. Bushkin explains why Carson, a voracious (and very talented) womanizer, felt he always had to be married; why he couldn�t visit his son in the hospital and wouldn�t attend his mother�s funeral; and much more. Johnny Carson is by turns shocking, poignant, and uproarious � written with a novelist�s eye for detail, a screenwriter�s ear for dialogue, and a knack for comic timing that Carson himself would relish.",
"PDF", 5.0, 7.2, 6.0, null),
(null, "978-0062430519", "The Woman Who Smashed Codes", "Dey Street Books", "2018-06-26", 448, "Biographies", 
"Joining the ranks of Hidden Figures and In the Garden of Beasts, the incredible true story of the greatest codebreaking duo that ever lived, an American woman and her husband who invented the modern science of cryptology together and used it to confront the evils of their time, solving puzzles that unmasked Nazi spies and helped win World War II.

In 1916, at the height of World War I, brilliant Shakespeare expert Elizebeth Smith went to work for an eccentric tycoon on his estate outside Chicago. The tycoon had close ties to the U.S. government, and he soon asked Elizebeth to apply her language skills to an exciting new venture: code-breaking. There she met the man who would become her husband, groundbreaking cryptologist William Friedman. Though she and Friedman are in many ways the ""Adam and Eve"" of the NSA, Elizebeth�s story, incredibly, has never been told.

In The Woman Who Smashed Codes, Jason Fagone chronicles the life of this extraordinary woman, who played an integral role in our nation�s history for forty years. After World War I, Smith used her talents to catch gangsters and smugglers during Prohibition, then accepted a covert mission to discover and expose Nazi spy rings that were spreading like wildfire across South America, advancing ever closer to the United States. As World War II raged, Elizebeth fought a highly classified battle of wits against Hitler�s Reich, cracking multiple versions of the Enigma machine used by German spies. Meanwhile, inside an Army vault in Washington, William worked furiously to break Purple, the Japanese version of Enigma�and eventually succeeded, at a terrible cost to his personal life.

Fagone unveils America�s code-breaking history through the prism of Smith�s life, bringing into focus the unforgettable events and colorful personalities that would help shape modern intelligence. Blending the lively pace and compelling detail that are the hallmarks of Erik Larson�s bestsellers with the atmosphere and intensity of The Imitation Game, The Woman Who Smashed Codes is page-turning popular history at its finest.",
"PDF", 0.5, 2.73, 1, null),
(null, "978-0718096212", "Churchill's Trial: Winston Churchill and the Salvation of Free Government", "Thomas Nelson", "2016-10-25", 416, "Biographies", 
"No statesman shaped the twentieth century more than Winston Churchill. To know Churchill is to understand the combinations of boldness and caution, assertiveness and humility, that define statesmanship at its best. With fresh perspective and insights based on decades of studying and teaching Churchill, Larry P. Arnn explores the greatest challenges faced by Churchill, both in war and in peacetime, in the context of Churchill�s abiding devotion to constitutionalism.",
"PDF", 0.5, 1.82, 1, null),
(null, "978-0544320819", "Coming Clean: A Memoir", "New Harvest", "2014-05-20", 272, "Biographies", 
"Kimberly Rae Miller is an immaculately put-together woman with a great career, a loving boyfriend, and a beautifully tidy apartment in Brooklyn. You would never guess that behind the closed doors of her family�s idyllic Long Island house hid teetering stacks of aging newspaper, broken computers, and boxes upon boxes of unused junk festering in every room�the product of her father�s painful and unending struggle with hoarding.

In this dazzling memoir, Miller brings to life her experience growing up in a rat-infested home, hiding her father�s shameful secret from friends for years, and the emotional burden that ultimately led to her suicide attempt. In beautiful prose, Miller sheds light on her complicated yet loving relationship with her parents, which has thrived in spite of the odds.

Coming Clean is a story about recognizing where you come from and understanding the relationships that define you. It is also a powerful story of recovery and redemption.",
"PDF", 0.6, 1.6, 1, null),
(null, "978-1328505682", "The Accidental President: Harry S. Truman and the Four Months That Changed the World", "Houghton Mifflin Harcourt", "2017-10-24", 464, "Biographies",
"The dramatic, pulse-pounding story of Harry Truman�s first four months in office, when this unlikely president had to take on Germany, Japan, Stalin, and the atomic bomb, with the fate of the world hanging in the balance.

Heroes are often defined as ordinary characters who get thrust into extraordinary circumstances, and through courage and a dash of luck, cement their place in history. Chosen as FDR�s fourth term Vice President for his well-praised work ethic, good judgment, and lack of enemies, Harry S. Truman--a Midwesterner who had no college degree and had never had the money to buy his own home--was the prototypical ordinary man. That is, until he was shockingly thrust in over his head after FDR�s sudden death. During the climactic months of the Second World War, Truman had to play judge and jury, pulling America to the forefront of the global stage. The first four months of Truman�s administration saw the founding of the United Nations, the fall of Berlin, victory at Okinawa, firebombings of Tokyo, the first atomic explosion, the Nazi surrender, the liberation of concentration camps, the mass starvation of Europe, the Potsdam Conference, the controversial decision to bomb Hiroshima and Nagasaki, the surrender of Imperial Japan, and finally, the end of World War II and the rise of the Cold War. No other president had ever faced so much in such a short period of time.

Tightly focused, meticulously researched, rendered with vivid detail and narrative verve, THE ACCIDENTAL PRESIDENT escorts readers into the situation room with Truman during this tumultuous, history-making 120 days, when the stakes were high and the challenge even higher. The result is narrative history of the highest order and a compelling look at a presidency with great relevance to our times.",
"PDF", 0.7, 2.39, 1.5, null),
(null, "978-1788541602", "Grant", "Penguin Press", "2017-10-10", 1098, "Biographies", 
"Ulysses S. Grant's life has typically been misunderstood. All too often he is caricatured as a chronic loser and an inept businessman, or as the triumphant but brutal Union general of the Civil War. But these stereotypes don't come close to capturing him, as Chernow shows in his masterful biography, the first to provide a complete understanding of the general and president whose fortunes rose and fell with dizzying speed and frequency.
 
Before the Civil War, Grant was flailing. His business ventures had ended dismally, and despite distinguished service in the Mexican War he ended up resigning from the army in disgrace amid recurring accusations of drunkenness. But in war, Grant began to realize his remarkable potential, soaring through the ranks of the Union army, prevailing at the battle of Shiloh and in the Vicksburg campaign, and ultimately defeating the legendary Confederate general Robert E. Lee. Along the way, Grant endeared himself to President Lincoln and became his most trusted general and the strategic genius of the war effort. Grant�s military fame translated into a two-term presidency, but one plagued by corruption scandals involving his closest staff members.

More important, he sought freedom and justice for black Americans, working to crush the Ku Klux Klan and earning the admiration of Frederick Douglass, who called him �the vigilant, firm, impartial, and wise protector of my race.� After his presidency, he was again brought low by a dashing young swindler on Wall Street, only to resuscitate his image by working with Mark Twain to publish his memoirs, which are recognized as a masterpiece of the genre.
 
With lucidity, breadth, and meticulousness, Chernow finds the threads that bind these disparate stories together, shedding new light on the man whom Walt Whitman described as �nothing heroic... and yet the greatest hero.� Chernow�s probing portrait of Grant's lifelong struggle with alcoholism transforms our understanding of the man at the deepest level. This is America's greatest biographer, bringing movingly to life one of our finest but most underappreciated presidents. The definitive biography, Grant is a grand synthesis of painstaking research and literary brilliance that makes sense of all sides of Grant's life, explaining how this simple Midwesterner could at once be so ordinary and so extraordinary. ",
"PDF", 13.99, 20.29, 16.99, null),
(null, "978-0316512589", "Obama: An Intimate Portrait", "Little, Brown and Company", "2017-11-07", 327, "Biographies", 
"During Barack Obama's two terms, Pete Souza was with the President during more crucial moments than anyone else--and he photographed them all. Souza captured nearly two million photographs of President Obama, in moments highly classified and disarmingly candid.

Obama: An Intimate Portrait reproduces more than 300 of Souza's most iconic photographs with fine-art print quality in an oversize collectible format. Together they document the most consequential hours of the Presidency--including the historic image of President Obama and his advisors in the Situation Room during the bin Laden mission--alongside unguarded moments with the President's family, his encounters with children, interactions with world leaders and cultural figures, and more.

Souza's photographs, with the behind-the-scenes captions and stories that accompany them, communicate the pace and power of our nation's highest office. They also reveal the spirit of the extraordinary man who became our President. We see President Obama lead our nation through monumental challenges, comfort us in calamity and loss, share in hard-won victories, and set a singular example to ""be kind and be useful,"" as he would instruct his daughters.

This book puts you in the White House with President Obama, and will be a treasured record of a landmark era in American history.",
"PDF", 14, 20.4, 17, null),
(null, "978-1538760857", "All-American Murder", "Little, Brown and Company", "2018-01-22", 401, "Biographies", 
"Football coaches, players, and fans called Aaron Hernandez unstoppable. His four-year-old daughter called him Daddy. The law called him inmate #174594.

He was a college All-American who became the youngest player in the NFL and later a Super Bowl veteran. He was a star tight end on the league-dominant New England Patriots, who extended his contract for a record $40 million. Aaron Hernandez's every move as a professional athlete played out in the headlines, yet he led a secret life-one that ended in a maximum security prison. What drove him to go so wrong, so fast?

All-American Murder is the first book to investigate - from the unique vantage point of the world's most popular writer - Aaron Hernandez's first-degree murder conviction and the mystery of his own untimely and shocking death. Drawing on original and in-depth reporting, this is an explosive true story of a life cut short in the dark shadow of fame. ",
"PDF", 8.5, 15.5, 11.99, null),
(null, "978-0345375568", "Dreadnought: Britain, Germany, and the Coming of the Great War", "Ballantine Books; Reprint edition", "1992-09-15", 1040, "Biographies",
"With the biographer�s rare genius for expressing the essence of extraordinary lives, Massie brings to life a crowd of glittery figures: the single-minded Admiral von Tirpitz; the young, ambitious Winston Churchill; the ruthless, sycophantic Chancellor Bernhard von B�low; Britain�s greatest twentieth-century foreign secretary, Sir Edward Grey; and Jacky Fisher, the eccentric admiral who revolutionized the British navy and brought forth the first true battleship, the H.M.S. Dreadnought.
 
Their story, and the story of the era, filled with misunderstandings, missed opportunities, and events leading to unintended conclusions, unfolds like a Greek tragedy in this powerful narrative. Intimately human and dramatic, Dreadnought is history at its most riveting.",
"PDF", 8.99, 14.75, 12.25, null),
(null, "978-0345438317", "Nicholas and Alexandra: The Classic Account of the Fall of the Romanov Dynasty", "Random House Trade Paperbacks; Reprint edition", "2000-02-01", 640, "Biographies", 
"In this commanding book, Pulitzer Prize�winning author Robert K. Massie sweeps readers back to the extraordinary world of Imperial Russia to tell the story of the Romanovs� lives: Nicholas�s political na�vet�, Alexandra�s obsession with the corrupt mystic Rasputin, and little Alexis�s brave struggle with hemophilia. Against a lavish backdrop of luxury and intrigue, Massie unfolds a powerful drama of passion and history�the story of a doomed empire and the death-marked royals who watched it crumble.",
"PDF", 5.99, 13.83, 11.5, null),
(null, "978-1503936904", "A River in Darkness: One Man's Escape from North Korea", "AmazonCrossing", "2018-01-01", 172, "Biographies", 
"Half-Korean, half-Japanese, Masaji Ishikawa has spent his whole life feeling like a man without a country. This feeling only deepened when his family moved from Japan to North Korea when Ishikawa was just thirteen years old, and unwittingly became members of the lowest social caste. His father, himself a Korean national, was lured to the new Communist country by promises of abundant work, education for his children, and a higher station in society. But the reality of their new life was far from utopian.

In this memoir translated from the original Japanese, Ishikawa candidly recounts his tumultuous upbringing and the brutal thirty-six years he spent living under a crushing totalitarian regime, as well as the challenges he faced repatriating to Japan after barely escaping North Korea with his life. A River in Darkness is not only a shocking portrait of life inside the country but a testament to the dignity�and indomitable nature�of the human spirit.",
"PDF", 2.2, 4.96, 3.95, null),
(null, "978-1681771335", "A Great and Terrible King: Edward I and the Forging of Britain", "Pegasus Books; 1 edition", "2016-06-14", 480, "Biographies", 
"Edward I is familiar to millions as ""Longshanks,"" conqueror of Scotland and nemesis of Sir William Wallace (in ""Braveheart""). Yet this story forms only the final chapter of the king's action-packed life. Earlier, Edward had defeated and killed the famous Simon de Montfort in battle; travelled to the Holy Land; conquered Wales, extinguishing forever its native rulers and constructing a magnificent chain of castles. He raised the greatest armies of the Middle Ages and summoned the largest parliaments; notoriously, he expelled all the Jews from his kingdom.The longest-lived of England's medieval kings, he fathered fifteen children with his first wife, Eleanor of Castile, and, after her death, he erected the Eleanor Crosses?the grandest funeral monuments ever fashioned for an English monarch.

In this book, Marc Morris examines afresh the forces that drove Edward throughout his relentless career: his character, his Christian faith, and his sense of England's destiny?a sense shaped in particular by the tales of the legendary King Arthur. He also explores the competing reasons that led Edward's opponents (including Robert Bruce) to resist him.

The result is a sweeping story, immaculately researched yet compellingly told, and a vivid picture of medieval Britain at the moment when its future was decided. 16 pages of color and B&W photographs",
"PDF", 3.45, 8.97, 6.99, null),
(null, "978-0812982282", "Victoria: The Queen: An Intimate Biography of the Woman Who Ruled an Empire", "Random House Trade Paperbacks; Reprint edition", "2017-10-03", 752, "Biographies",
"When Victoria was born, in 1819, the world was a very different place. Revolution would threaten many of Europe�s monarchies in the coming decades. In Britain, a generation of royals had indulged their whims at the public�s expense, and republican sentiment was growing. The Industrial Revolution was transforming the landscape, and the British Empire was commanding ever larger tracts of the globe. In a world where women were often powerless, during a century roiling with change, Victoria went on to rule the most powerful country on earth with a decisive hand.

Fifth in line to the throne at the time of her birth, Victoria was an ordinary woman thrust into an extraordinary role. As a girl, she defied her mother�s meddling and an adviser�s bullying, forging an iron will of her own. As a teenage queen, she eagerly grasped the crown and relished the freedom it brought her. At twenty, she fell passionately in love with Prince Albert of Saxe-Coburg and Gotha, eventually giving birth to nine children. She loved sex and delighted in power. She was outspoken with her ministers, overstepping conventional boundaries and asserting her opinions. After the death of her adored Albert, she began a controversial, intimate relationship with her servant John Brown. She survived eight assassination attempts over the course of her lifetime. And as science, technology, and democracy were dramatically reshaping the world, Victoria was a symbol of steadfastness and security�queen of a quarter of the world�s population at the height of the British Empire�s reach.

Drawing on sources that include fresh revelations about Victoria�s relationship with John Brown, Julia Baird brings vividly to life the fascinating story of a woman who struggled with so many of the things we do today: balancing work and family, raising children, navigating marital strife, losing parents, combating anxiety and self-doubt, finding an identity, searching for meaning.",
"PDF", 5.85, 13.83, 11.99, null),
(null, "978-0345298065", "Peter the Great: His Life and World", "Random House Trade Paperbacks; Reissue edition", "1981-10-12", 928, "Biographies", 
"Against the monumental canvas of seventeenth- and eighteenth-century Europe and Russia unfolds the magnificent story of Peter the Great, crowned co-tsar at the age of ten. Robert K. Massie delves deep into the life of this captivating historical figure, chronicling the pivotal events that shaped a boy into a legend�including his �incognito� travels in Europe, his unquenchable curiosity about Western ways, his obsession with the sea and establishment of the stupendous Russian navy, his creation of an unbeatable army, his transformation of Russia, and his relationships with those he loved most: Catherine, the robust yet gentle peasant, his loving mistress, wife, and successor; and Menshikov, the charming, bold, unscrupulous prince who rose to wealth and power through Peter�s friendship. Impetuous and stubborn, generous and cruel, tender and unforgiving, a man of enormous energy and complexity, Peter the Great is brought fully to life.",
"PDF", 8.95, 14.75, 12.99, null),
(null, "978-1250171085", "When They Call You a Terrorist: A Black Lives Matter Memoir", "St. Martin's Press; Reprint edition", "2018-01-16", 272, "Biographies", 
"From one of the co-founders of the Black Lives Matter movement comes a poetic memoir and reflection on humanity. Necessary and timely, Patrisse Cullors� story asks us to remember that protest in the interest of the most vulnerable comes from love. Leaders of the Black Lives Matter movement have been called terrorists, a threat to America. But in truth, they are loving women whose life experiences have led them to seek justice for those victimized by the powerful. In this meaningful, empowering account of survival, strength, and resilience, Patrisse Cullors and asha bandele seek to change the culture that declares innocent black life expendable.",
"PDF", 6.99, 12.2, 10.25, null);

--USERS MOCK DATA--
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('DawsonConsumer', 'dawsoncollege', 'Costumer', 'Ken', 'Fogel', 'Dawson', '3040 Sherbrooke St West', '', 'Montreal', 'QC', 'Canada', 'H3Z1A4', '', '514-123-4567', 'cst.send@gmail.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('DawsonManager', 'collegedawson', 'Manager', 'Ken', 'Fogel', 'Dawson', '3040 Sherbrooke St West', '', 'Montreal', 'QC', 'Canada', 'H3Z1A4', '', '514-456-1234', 'cst.receive@gmail.com', true);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('sramirez', 'booktopia', 'Manager', 'Sebastian', 'Ramirez', 'Dawson', '3040 Sherbrooke St West', '', 'Montreal', 'QC', 'Canada', 'H3Z1A4', '', '514-453-5644', 'sramirezdawson2017@gmail.com', true);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('mdaniels', 'booktopia', 'Manager', 'Marc-Daniel', 'Dialogo', 'Dawson', '3040 Sherbrooke St West', '', 'Montreal', 'QC', 'Canada', 'H3Z1A4', '', '514-555-1111', 'temp1@temp.com', true);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('jlouis', 'booktopia', 'Manager', 'Jephtia', 'Louis', 'Dawson', '3040 Sherbrooke St West', '', 'Montreal', 'QC', 'Canada', 'H3Z1A4', '', '514-234-0987', 'temp2@temp.com', true);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('shaidar', 'booktopia', 'Manager', 'Salman', 'Haidar', 'Dawson', '3040 Sherbrooke St West', '', 'Montreal', 'QC', 'Canada', 'H3Z1A4', '', '514-127-8934', 'temp3@temp.com', true);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('acutchie0', 'B5eazih', 'Dr', 'Amata', 'Cutchie', 'Meetz', '2067 Oak Valley Lane', '32 New Castle Center', 'Montreal', 'QC', 'Canada', 'A1A1A1', '', '514-555-0133', 'acutchie0@huffingtonpost.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('rkitchenman1', 'OqvvhnguH4fK', 'Ms', 'Rebekah', 'Kitchenman', 'Blogpad', '6164 Shopko Junction', '83 Marcy Lane', 'Montreal', 'QC', 'Canada', 'B2B2B2', '514-654-7854', '', 'rkitchenman1@networksolutions.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('rcolvill2', 'PTsrBElF', 'Honorable', 'Raphaela', 'Colvill', 'Jetwire', '06702 Summerview Terrace', '', 'Montreal', 'QC', 'Canada', 'C3C3C3', '514-567-7543', '', 'rcolvill2@xinhuanet.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('mmartineau3', 'SX6Kd6Q', 'Ms', 'Mack', 'Martineau', 'Skaboo', '60127 Orin Center', '', 'Montreal', 'QC', 'Canada', 'D4D4D4', '514-346-7321', '', 'mmartineau3@java.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('bwillbourne4', '4H290Yxx', 'Dr', 'Berthe', 'Willbourne', 'Devcast', '7 Cody Junction', '', 'Montreal', 'QC', 'Canada', 'E5E5E5', '514-456-2486', '', 'bwillbourne4@cisco.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('ylissandre5', 'gveFF2qbH', 'Rev', 'Yard', 'Lissandre', 'Realbridge', '42 Monterey Circle', '', 'Toronto', 'ON', 'Canada', 'F6F6F6', '416-435-7534', '', 'ylissandre5@mtv.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('mbisiker6', 'RSbScCtgZ', 'Ms', 'Matias', 'Bisiker', 'Livetube', '62709 Quincy Center', '88994 Warner Trail', 'Toronto', 'ON', 'Canada', 'G7G7G7', '416-567-3685', '', 'mbisiker6@examiner.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('jnaisey7', 'SpVBzhccHt', 'Rev', 'Jock', 'Naisey', 'Yozio', '48992 Larry Park', '21331 Eastwood Road', 'Toronto', 'ON', 'Canada', 'H8H8H8', '416-564-8653', '', 'jnaisey7@bloglines.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('msedcole8', 'oIQoG9GVRag7', 'Rev', 'Marguerite', 'Sedcole', 'Photobean', '4 Mayfield Way', '', 'Toronto', 'ON', 'Canada', 'I9II9I', '416-577-5555', '', 'msedcole8@comcast.net', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('vmarginson9', 'PzWV1LXz', 'Mr', 'Vivienne', 'Marginson', 'Bubblebox', '83 Mccormick Drive', '17562 North Court', 'Toronto', 'ON', 'Canada', 'J1J1J1', '416-544-5555', '', 'vmarginson9@bravesites.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('acompsona', 'nDg40fziT4S', 'Mrs', 'Adan', 'Compson', 'Trudoo', '65 Golf Course Terrace', '', 'Ottawa', 'ON', 'Canada', 'K2K2K2', '', '614-343-5666', 'acompsona@unblog.fr', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('lkelletb', 'xWjqBxR0KWU', 'Dr', 'Loraine', 'Kellet', 'Voonder', '4936 Hollow Ridge Trail', '180 Northport Place', 'Ottawa', 'ON', 'Canada', 'L3L3L3', '', '614-434-6444', 'lkelletb@spiegel.de', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('jmaleckyc', 'K18WsWLXe970', 'Rev', 'James', 'Malecky', 'Skivee', '52 Stoughton Plaza', '2 Loomis Avenue', 'Ottawa', 'ON', 'Canada', 'M4M4M4', '', '614-533-6778', 'jmaleckyc@sphinn.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('rscoggand', 'zkqrU1GnE', 'Rev', 'Roxanna', 'Scoggan', 'Photobug', '5 Morning Pass', '', 'Ottawa', 'ON', 'Canada', 'N5N5N5', '614-344-8665', '', 'rscoggand@samsung.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('dcraydone', 'C9eEsxakILJy', 'Honorable', 'Danny', 'Craydon', 'Zoozzy', '50 Talisman Junction', '34060 Wayridge Alley', 'Ottawa', 'ON', 'Canada', 'O6O6O6', '', '614-324-5455', 'dcraydone@constantcontact.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('lcuzenf', 'lZUkixT', 'Rev', 'Lina', 'Cuzen', 'Trudoo', '3 Reindahl Road', '32 Susan Avenue', 'Vancouver', 'BC', 'Canada', 'P7P7P7', '', '778-454-7765', 'lcuzenf@google.co.uk', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('cderislyg', 'pA7g5uqKIWj', 'Honorable', 'Carolynn', 'Derisly', 'Babbleopia', '6595 Corscot Hill', '', 'Vancouver', 'BC', 'Canada', 'Q8Q8Q8', '778-456-4555', '', 'cderislyg@census.gov', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('cmckelveyh', 'eCb0Lhw', 'Mrs', 'Cyndia', 'McKelvey', 'Kwinu', '5544 Nelson Way', '00 Tony Trail', 'Vancouver', 'BC', 'Canada', 'R9R9R9', '', '778-555-3232', 'cmckelveyh@forbes.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('hsimani', 'GFIVH2', 'Mrs', 'Hamid', 'Siman', 'Realcube', '35 High Crossing Parkway', '', 'Vancouver', 'BC', 'Canada', 'S0S0S0', '', '778-666-6666', 'hsimani@bravesites.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('dearyj', 'z1XFlx4', 'Honorable', 'Dion', 'Eary', 'Flipopia', '3 Blaine Park', '', 'Vancouver', 'BC', 'Canada', 'T1T1T1', '778-444-4444', '', 'dearyj@mysql.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('gemanuelek', 'iESYw8m5', 'Rev', 'Georgia', 'Emanuele', 'Geba', '870 Londonderry Place', '52944 Hanover Point', 'Surrey', 'BC', 'Canada', 'U2U2U2', '', '238-111-1111', 'gemanuelek@hostgator.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('adaniaudl', 'qyURv0O', 'Rev', 'Ansell', 'Daniaud', 'Eire', '24 Rockefeller Avenue', '', 'Ottawa', 'ON', 'Canada', 'V3V3V3', '', '614-222-2222', 'adaniaudl@google.de', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('pkwietekm', '1bQX0O0', 'Rev', 'Phil', 'Kwietek', 'Mudo', '38 Westridge Lane', '', 'Surrey', 'BC', 'Canada', 'W4W4W4', '', '238-333-3333', 'pkwietekm@youku.com', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('emccloryn', 'IBhXJL', 'Mr', 'Emilia', 'McClory', 'Tagchat', '02445 Kenwood Point', '1465 Burrows Center', 'Surrey', 'BC', 'Canada', 'X5X5X5', '', '238-555-5555', 'emccloryn@cpanel.net', false);
insert into USERS (USERNAME, PASSWORD, TITLE, FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS_1, ADDRESS_2, CITY, PROVINCE, COUNTRY, POSTAL_CODE, HOME_TELEPHONE, CELLPHONE, EMAIL, IS_MANAGER) values ('jboxhallo', 'kSwsAfS', 'Honorable', 'Joni', 'Boxhall', 'Voonte', '4528 Schlimgen Junction', '', 'Surrey', 'BC', 'Canada', 'Y6Y6Y6', '', '238-777-7777', 'jboxhallo@unc.edu', false);

--REVIEWS MOCK DATA--
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (72, 25, '2017-01-31 20:19:06', 4, 'platea dictumst morbi vestibulum velit id pretium iaculis diam erat fermentum justo nec condimentum neque sapien placerat ante nulla justo aliquam quis turpis eget elit sodales scelerisque mauris sit amet eros suspendisse accumsan tortor quis turpis sed ante vivamus tortor duis mattis', false);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (65, 6, '2017-06-19 15:44:18', 3, 'vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae mauris viverra diam vitae quam suspendisse potenti nullam porttitor lacus at turpis', false);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (87, 7, '2017-09-01 11:56:28', 5, 'adipiscing elit proin interdum mauris non ligula pellentesque ultrices phasellus id sapien in sapien iaculis congue vivamus metus arcu adipiscing molestie hendrerit at vulputate vitae nisl aenean lectus pellentesque eget nunc donec quis orci eget orci vehicula condimentum curabitur in libero ut massa volutpat convallis morbi odio', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (64, 11, '2017-02-22 14:05:42', 5, 'dictumst maecenas ut massa quis augue luctus tincidunt nulla mollis molestie lorem quisque ut erat curabitur gravida nisi at nibh in', false);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (91, 14, '2017-10-13 10:29:38', 5, 'diam vitae quam suspendisse potenti nullam porttitor lacus at turpis donec posuere metus vitae ipsum aliquam non mauris morbi non lectus aliquam sit amet diam in magna bibendum imperdiet nullam orci pede venenatis non sodales sed tincidunt eu felis fusce posuere felis sed lacus morbi sem', false);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (31, 25, '2017-10-31 08:10:00', 5, 'duis aliquam convallis nunc proin at turpis a pede posuere nonummy integer non velit donec diam neque vestibulum eget vulputate ut ultrices vel augue vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae donec pharetra magna vestibulum aliquet ultrices erat tortor sollicitudin mi sit amet lobortis', false);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (17, 2, '2017-09-21 02:19:53', 5, 'tristique est et tempus semper est quam pharetra magna ac consequat metus sapien ut nunc vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia', false);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (49, 25, '2018-01-12 11:12:41', 4, 'sagittis dui vel nisl duis ac nibh fusce lacus purus aliquet at feugiat non pretium quis lectus suspendisse potenti in eleifend quam a odio in hac habitasse platea dictumst maecenas ut massa quis augue luctus tincidunt nulla mollis molestie lorem quisque ut erat curabitur gravida nisi at', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (7, 17, '2017-03-08 15:00:40', 4, 'curabitur in libero ut massa volutpat convallis morbi odio odio elementum eu interdum eu tincidunt in leo maecenas pulvinar lobortis est phasellus sit amet erat nulla tempus vivamus in felis eu sapien cursus vestibulum proin eu mi nulla ac enim in tempor', false);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (57, 16, '2017-05-01 03:56:22', 1, 'varius nulla facilisi cras non velit nec nisi vulputate nonummy maecenas tincidunt lacus at velit vivamus vel nulla eget eros elementum pellentesque quisque porta volutpat erat quisque erat eros viverra eget congue eget semper rutrum nulla', false);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (88, 20, '2017-12-21 02:34:45', 4, 'orci luctus et ultrices posuere cubilia curae nulla dapibus dolor vel est donec odio justo sollicitudin ut suscipit a feugiat et eros vestibulum ac est lacinia nisi venenatis tristique fusce congue', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (56, 15, '2017-08-02 13:06:22', 2, 'non mauris morbi non lectus aliquam sit amet diam in magna bibendum imperdiet nullam orci pede venenatis non sodales sed tincidunt eu felis fusce posuere felis sed lacus morbi sem mauris laoreet ut rhoncus aliquet pulvinar sed nisl nunc rhoncus dui vel', false);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (33, 12, '2017-03-19 19:02:49', 3, 'sollicitudin ut suscipit a feugiat et eros vestibulum ac est lacinia nisi venenatis tristique fusce congue diam id ornare imperdiet sapien urna', false);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (64, 12, '2017-07-19 22:54:38', 1, 'bibendum felis sed interdum venenatis turpis enim blandit mi in porttitor pede justo eu massa donec dapibus duis at velit eu est congue elementum in hac habitasse platea dictumst', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (54, 3, '2017-12-25 17:01:41', 4, 'lacinia nisi venenatis tristique fusce congue diam id ornare imperdiet sapien urna pretium nisl ut volutpat sapien arcu sed augue aliquam erat volutpat in congue etiam justo etiam pretium iaculis justo in hac habitasse platea dictumst etiam faucibus cursus urna ut tellus nulla ut erat id mauris', false);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (55, 21, '2017-04-14 05:49:08', 3, 'est quam pharetra magna ac consequat metus sapien ut nunc vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae mauris viverra diam vitae quam suspendisse potenti nullam porttitor lacus at turpis donec posuere metus vitae ipsum aliquam non mauris', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (93, 16, '2017-04-28 09:04:35', 4, 'et magnis dis parturient montes nascetur ridiculus mus etiam vel augue vestibulum rutrum rutrum neque aenean auctor gravida sem praesent id massa id nisl venenatis lacinia aenean sit amet justo morbi ut odio cras mi pede malesuada in imperdiet et commodo vulputate justo in blandit ultrices enim lorem ipsum', false);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (90, 19, '2017-11-15 12:23:21', 1, 'potenti nullam porttitor lacus at turpis donec posuere metus vitae ipsum aliquam non mauris morbi non lectus aliquam sit amet diam in magna bibendum imperdiet nullam orci pede venenatis non sodales sed tincidunt eu felis', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (30, 16, '2017-12-21 14:16:16', 3, 'congue eget semper rutrum nulla nunc purus phasellus in felis donec semper sapien a libero nam dui proin leo odio porttitor id consequat in consequat ut nulla sed accumsan felis ut at dolor quis odio consequat varius integer ac leo pellentesque ultrices mattis odio donec vitae', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (22, 17, '2017-05-21 03:22:15', 4, 'maecenas tincidunt lacus at velit vivamus vel nulla eget eros elementum pellentesque quisque porta volutpat erat quisque erat eros viverra eget congue eget semper rutrum nulla nunc purus phasellus in felis donec semper sapien a libero nam', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (46, 5, '2017-02-27 00:29:06', 1, 'viverra diam vitae quam suspendisse potenti nullam porttitor lacus at turpis donec posuere metus vitae ipsum aliquam non mauris morbi non lectus aliquam sit amet diam in magna bibendum imperdiet nullam orci pede venenatis non sodales', false);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (63, 24, '2017-04-04 20:54:08', 5, 'eros viverra eget congue eget semper rutrum nulla nunc purus phasellus in felis donec semper sapien a libero nam dui proin leo odio porttitor id consequat in consequat ut nulla sed accumsan felis ut at', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (51, 19, '2017-12-30 00:43:21', 5, 'pretium nisl ut volutpat sapien arcu sed augue aliquam erat volutpat in congue etiam justo etiam pretium iaculis justo in hac habitasse platea dictumst etiam faucibus cursus urna ut tellus nulla ut erat id mauris vulputate elementum nullam varius nulla facilisi', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (90, 24, '2017-07-31 03:14:24', 5, 'bibendum felis sed interdum venenatis turpis enim blandit mi in porttitor pede justo eu massa donec dapibus duis at velit eu est congue elementum in hac habitasse platea dictumst morbi vestibulum velit id pretium iaculis diam erat fermentum justo nec', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (34, 5, '2017-09-12 19:41:34', 3, 'imperdiet sapien urna pretium nisl ut volutpat sapien arcu sed augue aliquam erat volutpat in congue etiam justo etiam pretium iaculis justo in hac', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (68, 3, '2017-03-18 23:33:41', 5, 'vel enim sit amet nunc viverra dapibus nulla suscipit ligula in lacus curabitur at ipsum ac tellus semper interdum mauris ullamcorper purus sit amet nulla', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (60, 23, '2017-08-21 08:44:32', 5, 'lacinia nisi venenatis tristique fusce congue diam id ornare imperdiet sapien urna pretium nisl ut volutpat sapien arcu sed augue aliquam erat volutpat in congue etiam justo etiam pretium iaculis justo in hac habitasse platea dictumst etiam faucibus cursus urna ut tellus nulla ut erat id mauris vulputate elementum nullam', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (43, 25, '2017-04-15 11:14:14', 5, 'tempus semper est quam pharetra magna ac consequat metus sapien ut nunc vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae mauris viverra diam vitae quam suspendisse potenti nullam porttitor lacus at turpis donec posuere metus vitae', true);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (100, 25, '2017-05-17 23:43:04', 4, 'dis parturient montes nascetur ridiculus mus vivamus vestibulum sagittis sapien cum sociis natoque penatibus et magnis dis parturient montes nascetur ridiculus mus etiam vel augue vestibulum rutrum rutrum neque aenean auctor gravida sem praesent id massa id nisl venenatis lacinia aenean sit amet', false);
insert into REVIEWS (BOOK_ID, USER_ID, REVIEW_DATE, RATING, REVIEW, APPROVAL_STATUS) values (55, 20, '2017-07-08 11:09:01', 3, 'amet eros suspendisse accumsan tortor quis turpis sed ante vivamus tortor duis mattis egestas metus aenean fermentum donec ut mauris eget massa tempor convallis nulla neque libero convallis eget eleifend luctus ultricies', false);

--TAXES--
insert into TAXES (PROVINCE, GST_RATE, PST_RATE, HST_RATE) values
('AB', 5, 0, 0),
('BC', 7, 5, 0),
('MB', 8, 5, 0),
('NB', 0, 0, 15),
('NL', 0, 0, 15),
('NS', 0, 0, 15),
('NT', 5, 0, 0),
('NU', 5, 0, 0),
('ON', 0, 0, 13),
('PE', 0, 0, 15),
('QC', 9.975, 5, 0),
('SK', 6, 7, 0),
('YT', 5, 0, 0);