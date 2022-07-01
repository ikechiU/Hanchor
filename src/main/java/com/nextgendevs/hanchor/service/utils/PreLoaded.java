package com.nextgendevs.hanchor.service.utils;

import com.nextgendevs.hanchor.shared.dto.AffirmationDto;
import com.nextgendevs.hanchor.shared.dto.GratitudeDto;
import com.nextgendevs.hanchor.shared.dto.LifeHackDto;
import com.nextgendevs.hanchor.shared.dto.QuoteDto;

import java.util.ArrayList;
import java.util.List;

public class PreLoaded {

    public List<GratitudeDto> gratitudeDtos() {
        GratitudeDto gratitude1 = new GratitudeDto();
        gratitude1.setTitle("Life");
        gratitude1.setMessage("You woke up this morning, breathing and with a heartbeat. " +
                "You get to enjoy today and hopefully wake up again tomorrow. " +
                "You get to see your family and friends, and enjoy food and life’s luxuries. It can sometimes be easy to " +
                "forget to be grateful for the most basic thing: life itself.");
        gratitude1.setImageUrl("");
        gratitude1.setImageId("0");

        GratitudeDto gratitude2 = new GratitudeDto();
        gratitude2.setTitle("Time");
        gratitude2.setMessage("Sometimes the clock seems to be our worst enemy. There’s so much to do, so little time. " +
                "Every minute, though, is precious. Step back and understand that time—and what we do with it—can change" +
                " the course of our day and maybe our lives. Be thankful for that ticking second hand, set goals, " +
                "and embrace every minute of the day…or, at least, try to embrace it. Sometimes it’s hard, sometimes " +
                "challenges make it harder. But time really is precious.");
        gratitude2.setImageUrl("");
        gratitude2.setImageId("0");

        List<GratitudeDto> dtos = new ArrayList<>();

        dtos.add(gratitude1);
        dtos.add(gratitude2);

        return dtos;
    }

    public List<QuoteDto> quoteDtos() {
        QuoteDto quote1 = new QuoteDto();
        quote1.setQuote("\"Learn as if you will live forever, live like you will die tomorrow.\" — Mahatma Gandhi");

        QuoteDto quote2 = new QuoteDto();
        quote2.setQuote("\"Stay away from those people who try to disparage your ambitions. Small minds will always do that," +
                " but great minds will give you a feeling that you can become great too. \" — Mark Twain");

        QuoteDto quote3 = new QuoteDto();
        quote3.setQuote("\"When you give joy to other people, you get more joy in return. You should give a good " +
                "thought to happiness that you can give out.\" — Eleanor Roosevelt ");

        QuoteDto quote4 = new QuoteDto();
        quote4.setQuote("\"When you change your thoughts, remember to also change your world. \" — Norman Vincent Peale ");

        QuoteDto quote5 = new QuoteDto();
        quote5.setQuote("\" It is only when we take chances, when our lives improve. " +
                "The initial and the most difficult risk that we need to take is to become honest.\" — Walter Anderson ");

        QuoteDto quote6 = new QuoteDto();
        quote6.setQuote("\" Nature has given us all the pieces required to achieve exceptional wellness and health, " +
                "but has left it to us to put these pieces together.\" — Diane McLaren");

        QuoteDto quote7 = new QuoteDto();
        quote7.setQuote("\"Success is not final; failure is not fatal: It is the courage to continue that counts. \" — Winston S. Churchill");

        QuoteDto quote8 = new QuoteDto();
        quote8.setQuote("\" Don’t let yesterday take up too much of today.\" — Will Rogers");

        QuoteDto quote9 = new QuoteDto();
        quote9.setQuote("\" If you are working on something that you really care about, you don’t have to be pushed. " +
                "The vision pulls you.\" — Steve Jobs");

        QuoteDto quote10 = new QuoteDto();
        quote10.setQuote("\"To know how much there is to know is the beginning of learning to live.\" — Dorothy West");

        List<QuoteDto> dtos = new ArrayList<>();
        dtos.add(quote1);
        dtos.add(quote2);
        dtos.add(quote3);
        dtos.add(quote4);
        dtos.add(quote5);
        dtos.add(quote6);
        dtos.add(quote7);
        dtos.add(quote8);
        dtos.add(quote9);
        dtos.add(quote10);

        return dtos;
    }

    public List<LifeHackDto> lifeHackDtos() {
        LifeHackDto lifeHackDto1 = new LifeHackDto();
        lifeHackDto1.setLifeHack("Sleep on your back. This permits even blood dissemination.");

        LifeHackDto lifeHackDto2 = new LifeHackDto();
        lifeHackDto2.setLifeHack("To eliminate little scratches on furnishings, use toothpaste.");

        LifeHackDto lifeHackDto3 = new LifeHackDto();
        lifeHackDto3.setLifeHack("Instead of rejecting, use ice cubes to eliminate table wax.");

        LifeHackDto lifeHackDto4 = new LifeHackDto();
        lifeHackDto4.setLifeHack(" Use vinegar to eliminate scents from the microwave.");

        LifeHackDto lifeHackDto5 = new LifeHackDto();
        lifeHackDto5.setLifeHack("For cleaning your blinds, use towel-wrapped utensils.");

        LifeHackDto lifeHackDto6 = new LifeHackDto();
        lifeHackDto6.setLifeHack("To dispose of garlic smell on hands, clean up with a cleanser and rub them on a spoon.");

        LifeHackDto lifeHackDto7 = new LifeHackDto();
        lifeHackDto7.setLifeHack("Try a jeans holder for a cookbook holder for a modest other option.");

        LifeHackDto lifeHackDto8 = new LifeHackDto();
        lifeHackDto8.setLifeHack("If you have various internet records, then use namechk.com to see " +
                "each site where your name has been utilized");

        LifeHackDto lifeHackDto9 = new LifeHackDto();
        lifeHackDto9.setLifeHack("Roll your garments to have more space in the pantry.");

        LifeHackDto lifeHackDto10 = new LifeHackDto();
        lifeHackDto10.setLifeHack("To make adequate space in the microwave, use glass to warm two plates.");

        LifeHackDto lifeHackDto11 = new LifeHackDto();
        lifeHackDto11.setLifeHack("Use foil to change any cooking dish into an ideal size.");

        LifeHackDto lifeHackDto12 = new LifeHackDto();
        lifeHackDto12.setLifeHack("Always place a piece of paper at the bottom of the dustbin. It would absorb the wet waste.");

        LifeHackDto lifeHackDto13 = new LifeHackDto();
        lifeHackDto13.setLifeHack("Drinking lukewarm lemon water every morning balances your body pH levels.");

        LifeHackDto lifeHackDto14 = new LifeHackDto();
        lifeHackDto14.setLifeHack("Using newspapers in shoes removes the stink.");

        LifeHackDto lifeHackDto15 = new LifeHackDto();
        lifeHackDto15.setLifeHack("If you chew apples every morning, your mouth won’t stink.");

        LifeHackDto lifeHackDto16 = new LifeHackDto();
        lifeHackDto16.setLifeHack("Citrus flavor candles leave the fragrance for longer hours.");

        LifeHackDto lifeHackDto17 = new LifeHackDto();
        lifeHackDto17.setLifeHack("If you have lots of keys, use nail polish or nail pain to keep track of each.");

        LifeHackDto lifeHackDto18 = new LifeHackDto();
        lifeHackDto18.setLifeHack("To rapidly and prudently call 911, just quickly press the iPhone home button multiple times.");

        LifeHackDto lifeHackDto19 = new LifeHackDto();
        lifeHackDto19.setLifeHack("Attach soft drink tabs to holders to make additional hanging space.");

        LifeHackDto lifeHackDto20 = new LifeHackDto();
        lifeHackDto20.setLifeHack("To know whether somebody is keen on your discussion, overlay your arms. " +
                "They will make a similar motion if they are.");

        LifeHackDto lifeHackDto21 = new LifeHackDto();
        lifeHackDto21.setLifeHack("Applying lemon juice on the spots blurs them.");

        LifeHackDto lifeHackDto22 = new LifeHackDto();
        lifeHackDto22.setLifeHack("Pay for things in real money to set aside cash.");

        LifeHackDto lifeHackDto23 = new LifeHackDto();
        lifeHackDto23.setLifeHack("To eliminate colored pencils separating the dividers, utilize a hairdryer.");

        LifeHackDto lifeHackDto24 = new LifeHackDto();
        lifeHackDto24.setLifeHack("To dispose of tacky imprint buildup, use cooking oil.");

        LifeHackDto lifeHackDto25 = new LifeHackDto();
        lifeHackDto25.setLifeHack("For cleaning your blinds, use towel-wrapped utensils.");

        LifeHackDto lifeHackDto26 = new LifeHackDto();
        lifeHackDto26.setLifeHack("With a clothespin, hold your toothbrush off the tacky ledge.");

        LifeHackDto lifeHackDto27 = new LifeHackDto();
        lifeHackDto27.setLifeHack("To keep the packs from staying, drill an opening in the lower part of your garbage bin.");

        LifeHackDto lifeHackDto28 = new LifeHackDto();
        lifeHackDto28.setLifeHack("Use bubbling water to kill weeds.");

        List<LifeHackDto> dtos = new ArrayList<>();
        dtos.add(lifeHackDto1);
        dtos.add(lifeHackDto2);
        dtos.add(lifeHackDto3);
        dtos.add(lifeHackDto4);
        dtos.add(lifeHackDto5);
        dtos.add(lifeHackDto6);
        dtos.add(lifeHackDto7);
        dtos.add(lifeHackDto8);
        dtos.add(lifeHackDto9);
        dtos.add(lifeHackDto10);
        dtos.add(lifeHackDto11);
        dtos.add(lifeHackDto12);
        dtos.add(lifeHackDto13);
        dtos.add(lifeHackDto14);
        dtos.add(lifeHackDto15);
        dtos.add(lifeHackDto16);
        dtos.add(lifeHackDto17);
        dtos.add(lifeHackDto18);
        dtos.add(lifeHackDto19);
        dtos.add(lifeHackDto20);
        dtos.add(lifeHackDto21);
        dtos.add(lifeHackDto22);
        dtos.add(lifeHackDto23);
        dtos.add(lifeHackDto24);
        dtos.add(lifeHackDto25);
        dtos.add(lifeHackDto26);
        dtos.add(lifeHackDto27);
        dtos.add(lifeHackDto28);

        return dtos;
    }

    public List<AffirmationDto> affirmationDtos() {
        AffirmationDto prosperity = new AffirmationDto();
        prosperity.setTitle("Prosperity");
        prosperity.setAffirmation("I can achieve whatever I set my mind to.");

        AffirmationDto love = new AffirmationDto();
        love.setTitle("Love");
        love.setAffirmation("I accept myself how I am and cultivate self-love.");

        AffirmationDto iAm = new AffirmationDto();
        iAm.setTitle("Iam");
        iAm.setAffirmation("I am worthy enough to tune into the abundant nature of who I can be and what I can create.");

        AffirmationDto family = new AffirmationDto();
        family.setTitle("Family");
        family.setAffirmation("Our family is blessed and highly favored.");

        AffirmationDto selfEsteem = new AffirmationDto();
        selfEsteem.setTitle("Self Esteem");
        selfEsteem.setAffirmation("I am a valuable human being.");

        AffirmationDto spiritual = new AffirmationDto();
        spiritual.setTitle("Spiritual");
        spiritual.setAffirmation("I feel the power of the divine spirit within me. " +
                "I am open to experiencing spiritual enlightenment.");

        AffirmationDto beauty = new AffirmationDto();
        beauty.setTitle("Beauty");
        beauty.setAffirmation("I don't need to change myself to feel attractive.\n" +
                "I accept myself and all my imperfections.\n" +
                "My imperfections make me beautiful.");

        AffirmationDto success = new AffirmationDto();
        success.setTitle("Success");
        success.setAffirmation("I know a positive attitude can bring me success.");

        AffirmationDto relationship = new AffirmationDto();
        relationship.setTitle("Relationship");
        relationship.setAffirmation("My partner and I deserve a long-lasting, happy, satisfying relationship.");

        AffirmationDto health = new AffirmationDto();
        health.setTitle("Health");
        health.setAffirmation("I deserve to feel healthy and vibrant.");

        AffirmationDto business = new AffirmationDto();
        business.setTitle("Business");
        business.setAffirmation("I believe in myself and trust in my abilities to succeed in all that I do." +
                "My work makes a difference.");

        AffirmationDto forgiveness = new AffirmationDto();
        forgiveness.setTitle("Self Forgiveness");
        forgiveness.setAffirmation("I let go of negative, harmful thoughts and habits.");

        AffirmationDto selfImprovement = new AffirmationDto();
        selfImprovement.setTitle("Self Improvement");
        selfImprovement.setAffirmation("I am getting better and better every day.");

        AffirmationDto iCan = new AffirmationDto();
        iCan.setTitle("Ican");
        iCan.setAffirmation("If it  is possible for anyone it possible for me too! Therefore, I can!");

        List<AffirmationDto> dtos = new ArrayList<>();
        dtos.add(prosperity);
        dtos.add(love);
        dtos.add(iAm);
        dtos.add(family);
        dtos.add(selfEsteem);
        dtos.add(spiritual);
        dtos.add(beauty);
        dtos.add(success);
        dtos.add(relationship);
        dtos.add(health);
        dtos.add(business);
        dtos.add(forgiveness);
        dtos.add(selfImprovement);
        dtos.add(iCan);

        return dtos;
    }

}
