<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230511034030 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE allusers ADD number VARCHAR(255) NOT NULL, ADD _2fa TINYINT(1) NOT NULL');
        $this->addSql('ALTER TABLE artistepostuler ADD notif TINYINT(1) NOT NULL');
        $this->addSql('ALTER TABLE rating_tutoriel DROP FOREIGN KEY FK_FC3A8F1A2417D8B7');
        $this->addSql('ALTER TABLE rating_tutoriel DROP FOREIGN KEY FK_FC3A8F1A761B5E6');
        $this->addSql('ALTER TABLE rating_tutoriel ADD CONSTRAINT FK_FC3A8F1A2417D8B7 FOREIGN KEY (idRater) REFERENCES allusers (id_user)');
        $this->addSql('ALTER TABLE rating_tutoriel ADD CONSTRAINT FK_FC3A8F1A761B5E6 FOREIGN KEY (tutorielId) REFERENCES tutoriel (id_tutoriel)');
        $this->addSql('ALTER TABLE tutoriel ADD CONSTRAINT FK_A2073AED5697F554 FOREIGN KEY (id_category) REFERENCES category (id_category)');
        $this->addSql('ALTER TABLE tutoriel ADD CONSTRAINT FK_A2073AED6B3CA4B FOREIGN KEY (id_user) REFERENCES allusers (id_user)');
        $this->addSql('ALTER TABLE video DROP FOREIGN KEY FK_7CC7DA2CF2DCD678');
        $this->addSql('ALTER TABLE video ADD CONSTRAINT FK_7CC7DA2CF2DCD678 FOREIGN KEY (id_tutoriel) REFERENCES tutoriel (id_tutoriel)');
        $this->addSql('ALTER TABLE view DROP FOREIGN KEY FK_FEFDAB8E6B3CA4B');
        $this->addSql('ALTER TABLE view DROP FOREIGN KEY FK_FEFDAB8E92429B1C');
        $this->addSql('ALTER TABLE view ADD CONSTRAINT FK_FEFDAB8E6B3CA4B FOREIGN KEY (id_user) REFERENCES allusers (id_user)');
        $this->addSql('ALTER TABLE view ADD CONSTRAINT FK_FEFDAB8E92429B1C FOREIGN KEY (id_video) REFERENCES video (id_video)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE allusers DROP number, DROP _2fa');
        $this->addSql('ALTER TABLE artistepostuler DROP notif');
        $this->addSql('ALTER TABLE rating_tutoriel DROP FOREIGN KEY FK_FC3A8F1A761B5E6');
        $this->addSql('ALTER TABLE rating_tutoriel DROP FOREIGN KEY FK_FC3A8F1A2417D8B7');
        $this->addSql('ALTER TABLE rating_tutoriel ADD CONSTRAINT FK_FC3A8F1A761B5E6 FOREIGN KEY (tutorielId) REFERENCES tutoriel (id_tutoriel) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE rating_tutoriel ADD CONSTRAINT FK_FC3A8F1A2417D8B7 FOREIGN KEY (idRater) REFERENCES allusers (id_user) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE tutoriel DROP FOREIGN KEY FK_A2073AED5697F554');
        $this->addSql('ALTER TABLE tutoriel DROP FOREIGN KEY FK_A2073AED6B3CA4B');
        $this->addSql('ALTER TABLE video DROP FOREIGN KEY FK_7CC7DA2CF2DCD678');
        $this->addSql('ALTER TABLE video ADD CONSTRAINT FK_7CC7DA2CF2DCD678 FOREIGN KEY (id_tutoriel) REFERENCES tutoriel (id_tutoriel) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE view DROP FOREIGN KEY FK_FEFDAB8E6B3CA4B');
        $this->addSql('ALTER TABLE view DROP FOREIGN KEY FK_FEFDAB8E92429B1C');
        $this->addSql('ALTER TABLE view ADD CONSTRAINT FK_FEFDAB8E6B3CA4B FOREIGN KEY (id_user) REFERENCES allusers (id_user) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE view ADD CONSTRAINT FK_FEFDAB8E92429B1C FOREIGN KEY (id_video) REFERENCES video (id_video) ON UPDATE CASCADE ON DELETE CASCADE');
    }
}
